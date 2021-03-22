package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 字典同步枚举注册器
 *
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
    private transient DictEnumSyncProcessor globalSyncProcessor;

    /**
     * 字典数据提供者
     */
    private transient DictDetailsProvider provider;

    /**
     * 同步器集合
     */
    private List<DictEnumSynchronizer> synchronizers = new ArrayList<>();

    /**
     * 单例对象持有者
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
    private DictEnumSyncRegistry() {
        putSpiSyncProcessor();
    }


    /**
     * 设置字典数据提供者
     * 只要
     *
     * @param provider 提供者
     * @return this
     */
    public DictEnumSyncRegistry setProvider(DictDetailsProvider provider) {
        this.provider = provider;
        return this;
    }

    /**
     * 注册同步器
     *
     * @param dictType      数据字典类型
     * @param enumType      枚举类型
     * @param syncProcessor 同步处理器
     * @return this
     */
    public DictEnumSyncRegistry register(String dictType, Class<? extends Enum<?>> enumType, DictEnumSyncProcessor syncProcessor) {
        Assert.isTrue(StrUtil.isNotBlank(dictType), "dictType must not be blank");
        Assert.notNull(enumType, "enumType must not be null");
        Assert.notNull(syncProcessor, "syncProcessor must not be null");
        if (getSynchronizer(dictType) == null) {
            DictEnumSynchronizer synchronizer = new DictEnumSynchronizer(dictType, enumType, syncProcessor);
            synchronizers.add(synchronizer);
        }
        return this;
    }


    /**
     * 注册同步器,使用spi同步处理器
     *
     * @param dictType 数据字典类型
     * @param enumType 枚举类
     * @return this 同步处理器
     */
    public DictEnumSyncRegistry register(String dictType, Class<? extends Enum<?>> enumType) {
        if (globalSyncProcessor != null) {
            register(dictType, enumType, globalSyncProcessor);
        } else {
            log.warn("Cannot find spi GlobalSyncProcessor,Please check");
        }
        return this;
    }

    /**
     * 获取同步器
     *
     * @param dictType 数据字典类型
     * @return 同步器
     */
    public DictEnumSynchronizer getSynchronizer(String dictType) {
        return synchronizers.stream().filter(synchronizer -> Objects.equals(synchronizer.getDictType(), dictType)).findFirst().orElse(null);
    }


    /**
     * 执行同步处理器
     *
     * @param dictType 数据字典类型
     */
    public void sync(String dictType) {
        DictEnumSynchronizer synchronizer = getSynchronizer(dictType);
        if (synchronizer == null) {
            log.info("cannot find synchronizer from dictType :" + dictType);
            return;
        }
        Collection<DictDetails> dictData = null;
        if (provider != null) {
            try {
                dictData = provider.loadDictsByDictType(dictType);
            } catch (Exception e) {
                throw new LoadDictDetailsException("Failed to load dictData of dictType :" + dictType, e);
            }
        }
        synchronizer.processSync(dictData);
    }


    /**
     * 执行全部同步处理器
     */
    public void syncAll() {
        if (synchronizers.isEmpty()) {
            log.info("no synchronizer has been registered");
        }
        Collection<DictDetails> dictData = null;
        if (provider != null) {
            try {
                dictData = provider.loadAllDicts();
            } catch (Exception e) {
                throw new LoadDictDetailsException("Failed to load dictData", e);
            }

        }
        for (DictEnumSynchronizer synchronizer : synchronizers) {
            if (dictData != null) {
                dictData.removeIf(dict -> !Objects.equals(dict.getDictType(), synchronizer.getDictType()));
            }
            try {
                synchronizer.processSync(dictData);
            } catch (DictEnumSyncException e) {
                // 打印错误日志,并继续往后执行
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 读取Spi SyncProcessor并设置到全局处理器中
     */
    private void putSpiSyncProcessor() {
        ServiceLoader<DictEnumSyncProcessor> serviceLoader = ServiceLoader.load(DictEnumSyncProcessor.class);
        Iterator<DictEnumSyncProcessor> iterator = serviceLoader.iterator();
        boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            log.info("Cannot find spi GlobalSyncProcessor");
            return;
        }
        // 取第一个
        this.globalSyncProcessor = iterator.next();
        log.info("find spi GlobalSyncProcessor :" + globalSyncProcessor.getClass().getName());
    }
}