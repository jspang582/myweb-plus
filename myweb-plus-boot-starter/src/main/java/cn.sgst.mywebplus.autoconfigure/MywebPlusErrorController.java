package cn.sgst.mywebplus.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

/**
 * 装配自定义全局异常处理器替换Spring Boot对错误的处理
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 10:47
 */
@Controller
@RequestMapping("${myweb-plus.error.path:/error}")
@Slf4j
public class MywebPlusErrorController extends AbstractErrorController {

    private final ErrorAttributes errorAttributes;
    private final MywebPlusErrorProperties errorProperties;
    /**
     * 返回json格式时是否永远返回200状态码
     */
    private final boolean httpStatusAlways200;
    /**
     * 返回json格式时是否显示错误详情
     */
    private final boolean showErrorDesc;

    public MywebPlusErrorController(ErrorAttributes errorAttributes,
                                    ErrorViewResolver errorViewResolver,
                                    MywebPlusErrorProperties errorProperties) {
        super(errorAttributes, Collections.singletonList(errorViewResolver));
        this.errorAttributes = errorAttributes;
        this.errorProperties = errorProperties;
        this.httpStatusAlways200 = errorProperties.isHttpStatusAlways200();
        this.showErrorDesc = errorProperties.isShowErrorDesc();
    }

    /**
     * 返回页面
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        Map<String, Object> model = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        if (modelAndView != null) {
            return modelAndView;
        }
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(this.error(request).getBody()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 返回json
     */
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (httpStatusAlways200) {
            status = HttpStatus.OK;
        }
        Map<String, Object> body = getErrorAttributes(request,
                false);
        if(!showErrorDesc) {
            body.remove("errorDesc");
        }
        return new ResponseEntity<>(body, status);
    }


    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }


    /**
     * 重写它是因为spring-boot 2.3.0版本兼容问题
     */
    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    /**
     * 业务异常code适配HttpStatus
     */
    @Override
    protected HttpStatus getStatus(HttpServletRequest request) {
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
                request, false));
        // 获取状态码
        Integer statusCode = (Integer) model.get("code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
