package cn.sgst.mywebplus.core.validation.group.support;

import cn.hutool.core.util.TypeUtil;
import cn.sgst.mywebplus.core.SpringContextHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 基于{@link DefaultGroupSequenceProvider}的抽象实现
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/12/26 12:23
 */
public abstract class AbstractGroupSequenceProvider<T> implements DefaultGroupSequenceProvider<T> {

    @Override
    public final List<Class<?>> getValidationGroups(T object) {
        Set<Class<?>> defaultGroupSequence = new HashSet<>();
        // 获取泛型的真实类型
        Class<?> tClass = (Class<?>) TypeUtil.getTypeArgument(getClass());
        defaultGroupSequence.add(tClass);
        if (object != null) {
            // 方法可能会执行多次,只执行一次
            RequestHolder requestHolder = SpringContextHolder.getBean(RequestHolder.class);
            if (requestHolder.isFirst(tClass.getName())) {
                doSetValidationGroups(object, defaultGroupSequence);
                requestHolder.setNotFirst(tClass.getName());
            }
        }
        return new ArrayList<>(defaultGroupSequence);
    }


    /**
     * 由子类去真正实现动态组的逻辑
     * @param object javaBean
     * @param groupSequence 动态组集合
     */
    protected abstract void doSetValidationGroups(T object, Set<Class<?>> groupSequence);



    /**
     * 存放Request作用域变量
     */
    @Component
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    @Getter
    @Setter
    static class RequestHolder {

        private final List<Holder> holders = new ArrayList<>();

        private boolean isFirst(String className) {
            Optional<Holder> optional = holders.stream().filter(holder -> Objects.equals(className, holder.getClassName())).findFirst();
            boolean present = optional.isPresent();
            if (!present) {
                holders.add(new Holder(true, className));
                return true;
            } else {
                return optional.get().isFirst();
            }
        }

        private void setNotFirst(String className) {
            for (Holder holder : holders) {
                if (Objects.equals(holder.getClassName(), className)) {
                    holder.setFirst(false);
                }
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        private class Holder {
            private boolean first;
            private String className;
        }
    }
}
