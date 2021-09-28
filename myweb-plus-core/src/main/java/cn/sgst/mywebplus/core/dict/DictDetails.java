package cn.sgst.mywebplus.core.dict;

import java.io.Serializable;

/**
 * 字典详情接口
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 9:49
 */
public interface DictDetails extends Serializable, Comparable<DictDetails> {

    /**
     * 获取字典类型
     *
     * @return String
     */
    String getDictType();

    /**
     * 获取字典Value
     *
     * @return Object
     */
    Object getDictValue();


    /**
     * 获取字典Text
     *
     * @return String
     */
    String getDictText();


    /**
     * 获取权重
     *
     * @return 权重
     */
    default Comparable<?> getWeight() {
        return 0;
    }

    /**
     * 比较逻辑,默认根据weight排序,也可以重写该方法自定义排序逻辑
     * @param details 字典数据
     * @return 比较结果
     */
    @SuppressWarnings({"unchecked", "rawtypes", "NullableProblems"})
    @Override
    default int compareTo(DictDetails details) {
        final Comparable weight = this.getWeight();
        if (null != weight) {
            final Comparable weightOther = details.getWeight();
            return weight.compareTo(weightOther);
        }
        return 0;
    }
}
