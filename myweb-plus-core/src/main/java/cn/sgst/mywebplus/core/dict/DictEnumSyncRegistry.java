package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 14:13
 */
@Getter
@Slf4j
public class DictEnumSyncRegistry implements Serializable {

    /**
     * spi必须保证线程安全
     */
    private transient static DictEnumSyncProcessor globalSyncProcessor;

    private List<DictEnumSynchronizer> synchronizers = new ArrayList<>();

    static {
        ServiceLoader<DictEnumSyncProcessor> serviceLoader = ServiceLoader.load(DictEnumSyncProcessor.class);
        for (DictEnumSyncProcessor syncProcessor : serviceLoader) {
            // 取第一个
            DictEnumSyncRegistry.globalSyncProcessor = syncProcessor;
            break;
        }
        if(globalSyncProcessor == null) {
            log.info("cannot find spi GlobalSyncProcessor");
        }else {
            log.info("find spi GlobalSyncProcessor :" + globalSyncProcessor.getClass().getName());
        }
    }

    /**
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static final DictEnumSyncRegistry INSTANCE = new DictEnumSyncRegistry();
    }

    /**
     * 获得单例的 DictEnumSyncRegistry
     *
     * @return DictEnumSyncRegistry
     */
    public static DictEnumSyncRegistry getInstance() {
        return DictEnumSyncRegistry.SingletonHolder.INSTANCE;
    }

    /**
     * 私有构造方法,保证单例
     */
    private DictEnumSyncRegistry() {}

    public DictEnumSyncRegistry register(String dictType, Class<? extends Enum<?>> enumType,DictEnumSyncProcessor syncProcessor) {
        Assert.isTrue(StrUtil.isNotBlank(dictType),"dictType must not be blank");
        Assert.notNull(enumType,"enumType must not be null");
        Assert.notNull(syncProcessor,"syncProcessor must not be null");
        DictEnumSynchronizer synchronizer = new DictEnumSynchronizer(dictType, enumType, syncProcessor);
        synchronizers.add(synchronizer);
        return this;
    }

    public DictEnumSyncRegistry register(String dictType,Class<? extends Enum<?>> enumType) {
        Assert.isTrue(StrUtil.isNotBlank(dictType),"dictType must not be blank");
        Assert.notNull(enumType,"enumType must not be null");
        if(globalSyncProcessor != null) {
            DictEnumSynchronizer synchronizer = new DictEnumSynchronizer(dictType, enumType, globalSyncProcessor);
            synchronizers.add(synchronizer);
        }
        return this;
    }

    public DictEnumSynchronizer getSynchronizer(String dictType) {
        return synchronizers.stream().filter(synchronizer -> Objects.equals(synchronizer.getDictType(),dictType)).findFirst().orElse(null);
    }



    public void sync(String dictType) {
        DictEnumSynchronizer synchronizer = getSynchronizer(dictType);
        if(synchronizer == null) {
            log.info("cannot find synchronizer from dictType :" + dictType);
            return;
        }
        synchronizer.processSync();
    }

    public void syncAll() {
        if( synchronizers.isEmpty()) {
            log.info("no synchronizer has been registered");
        }
        for (DictEnumSynchronizer synchronizer : synchronizers) {
            try {
                synchronizer.processSync();
            }catch (DictEnumSyncException e) {
                // 打印错误日志,并继续往后执行
                log.error(e.getMessage(),e);
            }
        }
    }
}