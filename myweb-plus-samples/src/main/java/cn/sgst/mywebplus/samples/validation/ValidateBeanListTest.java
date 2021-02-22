package cn.sgst.mywebplus.samples.validation;

import cn.sgst.mywebplus.core.validation.ValidList;
import cn.sgst.mywebplus.core.validation.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.Set;

/**
 * List集合校验
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/22 15:22
 */
@SpringBootTest
public class ValidateBeanListTest {


    @Test
    public void validate() {
        User user = new User();
        user.setAge(18);
        Set<ConstraintViolation<ValidList<User>>> violations = ValidationUtils.validate(new ValidList<>(Arrays.asList(user)), Default.class);
        System.out.println(violations);
    }
}
