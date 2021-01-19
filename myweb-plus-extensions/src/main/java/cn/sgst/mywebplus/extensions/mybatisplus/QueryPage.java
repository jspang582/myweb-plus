package cn.sgst.mywebplus.extensions.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 基于实体类的条件分页查询
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/11 16:24
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage<T extends G, G> extends Page<G> {

    /**
     * 查询条件
     */
    private T params;

    public QueryPage(long current, long size, T params) {
        super(current, size);
        this.params = params;
    }

    public void setPage(long page) {
        setCurrent(page);
    }

    public void setLimit(long size) {
        setSize(size);
    }
}
