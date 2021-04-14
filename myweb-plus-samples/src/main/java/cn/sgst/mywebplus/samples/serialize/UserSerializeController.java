package cn.sgst.mywebplus.samples.serialize;

import cn.hutool.core.convert.Convert;
import cn.sgst.mywebplus.core.api.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:20
 */
@RestController
@RequestMapping("/userserialize")
public class UserSerializeController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/1")
    public ApiResult<Map> getUser()  {
        UserVO userVO = new UserVO();
        userVO.setAge(18);
        userVO.setName("张三");
        userVO.setHobby(HobbyEnum.Basketball.getValue());
        userVO.setHobbies(Arrays.asList(HobbyEnum.JUMP,HobbyEnum.SING));
        userVO.setSex(SexEnum.FEMALE);
        Map map = objectMapper.convertValue(userVO, Map.class);
        return ApiResult.success(map);
    }
}
