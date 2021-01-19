package cn.sgst.mywebplus.samples.exception.controller;

import cn.sgst.mywebplus.core.api.ApiResult;
import cn.sgst.mywebplus.samples.exception.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web访问
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 17:11
 */
@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello0")
    public ApiResult<Void> hello0() {
        return ApiResult.success();
    }


    @RequestMapping(value = "/hello1")
    public ApiResult<String> hello1() {
        return ApiResult.success("hello");
    }

    @RequestMapping(value = "/hello2")
    public ApiResult<String> hello2() {
        Integer a = Integer.parseInt("ss");
        return ApiResult.success("hello");
    }

    @RequestMapping(value = "/hello3")
    public ApiResult<String> hello3() {
        throw new MyException(401,"不允许访问");
    }
}
