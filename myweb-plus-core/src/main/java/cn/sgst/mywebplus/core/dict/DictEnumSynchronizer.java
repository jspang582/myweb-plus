package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.util.ValidateUtil;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 数据字典同步枚举同步器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 13:55你
 */
@Getter
public class DictEnumSynchronizer implements Serializable {

    /**
     * 字典类型
     */
    private final String dictType;

    /**
     * 枚举类型
     */
    private final Class<? extends Enum<?>> enumType;


    /**
     * 同步处理器
     */
    private transient final DictEnumSyncProcessor syncProcessor;


    public DictEnumSynchronizer(String dictType, Class<? extends Enum<?>> enumType, DictEnumSyncProcessor syncProcessor) {
        Assert.isTrue(StrUtil.isNotBlank(dictType), "dictType must not be blank");
        Assert.notNull(enumType, "enumType must not be null");
        Assert.notNull(syncProcessor, "syncProcessor must not be null");
        this.dictType = dictType;
        this.enumType = enumType;
        this.syncProcessor = syncProcessor;
    }


    /**
     * 处理同步
     *
     * @param dictData 字典数据
     */
    public void processSync(@Nullable Collection<DictDetails> dictData) throws DictEnumSyncException {
        if (dictData == null) {
            dictData = new ArrayList<>();
        }
        // 过滤掉空值
        dictData.removeIf(dict -> ValidateUtil.isEmpty(dict.getDictValue()) || StrUtil.isBlank(dict.getDictText()));
        try {
            syncProcessor.processSync(dictType, dictData, enumType);
        } catch (Exception e) {
            throw new DictEnumSyncException(dictType, enumType, e);
        }
    }

    public String getDictType() {
        return dictType;
    }

}
