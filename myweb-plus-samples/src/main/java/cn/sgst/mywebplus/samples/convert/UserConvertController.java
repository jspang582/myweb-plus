package cn.sgst.mywebplus.samples.convert;

import cn.sgst.mywebplus.core.api.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 14:53
 */
@RestController
@RequestMapping("/userconvert")
public class UserConvertController {

    /**
     * form表单提交
     */
    @PostMapping("/add")
    public ApiResult<User> add(User user) {
        System.out.println(user);
        return ApiResult.success(user);
    }


    /**
     * json提交
     */
    @PostMapping("/add1")
    public ApiResult<User> add1(@RequestBody User user) {
        System.out.println(user);
        return ApiResult.success(user);
    }
}
