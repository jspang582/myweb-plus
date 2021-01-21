package cn.sgst.mywebplus.samples.validation;

import cn.sgst.mywebplus.core.validation.group.Add;
import cn.sgst.mywebplus.core.validation.group.support.AbstractGroupSequenceProvider;
import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 需要校验的用户对象
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/21 9:27
 */
@Data
@GroupSequenceProvider(UserGroupProvider.class)
public class User {

    @Null(message = "ID必须为null",groups = {Add.class})
    private String id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotNull(message = "年龄不能为空")
    private Integer age;
}
