package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.api.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:20
 */
@RestController
@RequestMapping("/userserialize")
public class UserSerializeController {


    @GetMapping("/1")
    public ApiResult<UserVO> getUser() {
        UserVO userVO = new UserVO();
        userVO.setAge(18);
        userVO.setName("张三");
        userVO.setSex(SexEnum.FEMALE);
        return ApiResult.success(userVO);
    }
}
