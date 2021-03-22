package cn.sgst.mywebplus.core.dict;

import lombok.*;

/**
 * DictDetails的简单实现
 *
 * @see DictDetails
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 14:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SimpleDict extends Dict<Object, String> implements DictDetails {

    /**
     * 字典类型
     */
    private String dictType;


    public SimpleDict(String dictType, Object value, String text) {
        super(value, text);
        this.dictType = dictType;
    }

    @Override
    public String getDictType() {
        return dictType;
    }

    @Override
    public Object getDictValue() {
        return super.getValue();
    }


    @Override
    public String getDictText() {
        return super.getText();
    }
}
