package cn.sgst.mywebplus.core.validation;

import cn.sgst.mywebplus.core.validation.group.Required;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import java.util.Set;

/**
 * Payload Required 违反约束处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/19 23:52
 */
public class PayloadRequiredViolationProcessor implements RequiredViolationProcessor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsRequired(@NonNull ConstraintViolation constraintViolation) {
        Assert.notNull(constraintViolation, "constraintViolation must not be null");
        Set<Class<? extends Payload>> payloads = constraintViolation.getConstraintDescriptor().getPayload();
        for (Class<? extends Payload> payload : payloads) {
            if (payload == Required.class) {
                return true;
            }
        }
        return false;
    }
}
