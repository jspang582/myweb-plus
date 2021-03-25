package cn.sgst.mywebplus.core.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import java.util.Set;

/**
 * 基于Payload的违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 10:10
 */
public abstract class AbstractPayloadConstraintViolationFilter implements ConstraintViolationClassBasedFilter {

    /**
     * 如果过滤Class等于groups中的某一个类则满足过滤
     *
     * @param constraintViolation 违反约束
     * @return true/false
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean accept(ConstraintViolation constraintViolation) {
        Set<Class<? extends Payload>> payloads = constraintViolation.getConstraintDescriptor().getPayload();
        for (Class<? extends Payload> payload : payloads) {
            if (payload == acceptClass()) {
                return true;
            }
        }
        return false;
    }
}
