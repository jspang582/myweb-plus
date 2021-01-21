package cn.sgst.mywebplus.samples.validation.controller;

import cn.sgst.mywebplus.core.api.ApiResult;
import cn.sgst.mywebplus.core.validation.group.Add;
import cn.sgst.mywebplus.samples.validation.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

/**
 * 用户-前端控制器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/21 9:46
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    /**
     * 新增用户
     */
    @PostMapping(value = "/save")
    public ApiResult<Void> saveUser(@RequestBody @Validated({Add.class,Default.class}) User user) {
        log.info("user",user);
        return ApiResult.success();
    }
}
