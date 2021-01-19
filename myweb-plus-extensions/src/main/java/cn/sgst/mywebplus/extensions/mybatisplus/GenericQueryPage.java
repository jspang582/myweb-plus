package cn.sgst.mywebplus.extensions.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 一般的条件分页查询
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/11 16:27
 */
@AllArgsConstructor
public class GenericQueryPage<T> extends QueryPage<T,Object> {

    public GenericQueryPage(T params) {
        super(params);
    }

    public GenericQueryPage(long current, long size, T params) {
        super(current, size,params);
    }
}
