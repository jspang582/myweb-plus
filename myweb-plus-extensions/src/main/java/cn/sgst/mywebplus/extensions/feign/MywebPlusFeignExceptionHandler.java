package cn.sgst.mywebplus.extensions.feign;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局处理FeignException
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:46
 */
@ControllerAdvice
@Slf4j
public class MywebPlusFeignExceptionHandler {

    @ExceptionHandler(value = FeignException.class)
    public String handleSgstFeignException(HttpServletRequest req, FeignException ex) {
        log.error("远程调用服务出错",ex);
        req.setAttribute(RequestDispatcher.ERROR_STATUS_CODE,ex.status());
        req.setAttribute("mywebplus.servlet.error.message",ex.getMessage());
        return "forward:/error";
    }
}
