package cn.sgst.mywebplus.core.dict;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 数据字典同步枚举整合spring框架
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/10/20 22:40
 */
public class DictEnumSyncFramework implements DictEnumSyncCommand, InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private final DictEnumSyncRegistry registry = DictEnumSyncRegistry.getInstance();

    @Override
    public void sync(String dictType) {
        registry.sync(dictType);
    }

    @Override
    public void syncAll() {
        registry.syncAll();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 暂时先不做什么事
    }

    /**
     * spring容器启动完成后执行同步全部
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.syncAll();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final DictEnumSyncRegistry registry = DictEnumSyncRegistry.getInstance();

        Builder() {
        }

        public Builder provider(DictDetailsProvider provider) {
            registry.setProvider(provider);
            return this;
        }

        public Builder register(String dictType, Class<? extends Enum<?>> enumType) {
            registry.register(dictType, enumType);
            return this;
        }

        public Builder register(String dictType, Class<? extends Enum<?>> enumType, DictEnumSyncProcessor syncProcessor) {
            registry.register(dictType, enumType, syncProcessor);
            return this;
        }

        public DictEnumSyncFramework build() {
            return new DictEnumSyncFramework();
        }
    }
}
