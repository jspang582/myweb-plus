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
public class SimpleDict extends Dict<String, String> implements DictDetails {

    /**
     * 字典类型
     */
    private String dictType;


    public SimpleDict(String dictType, String value, String text) {
        super(value, text);
        this.dictType = dictType;
    }

    @Override
    public String getDictType() {
        return dictType;
    }

    @Override
    public String getDictValue() {
        return super.getValue();
    }

    @Override
    public String getDictText() {
        return super.getText();
    }
}
