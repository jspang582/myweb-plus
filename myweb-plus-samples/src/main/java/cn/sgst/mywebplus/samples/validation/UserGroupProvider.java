package cn.sgst.mywebplus.samples.validation;

import cn.sgst.mywebplus.core.validation.group.support.AbstractGroupSequenceProvider;

import java.util.Set;

/**
 * User动态校验
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/21 9:44
 */
public class UserGroupProvider extends AbstractGroupSequenceProvider<User> {
    @Override
    protected void doSetValidationGroups(User user, Set<Class<?>> groupSequence) {
        // 业务逻辑
    }
}
