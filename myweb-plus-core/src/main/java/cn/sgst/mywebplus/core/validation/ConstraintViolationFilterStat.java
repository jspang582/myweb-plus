package cn.sgst.mywebplus.core.validation;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * 违反约束过滤统计
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 9:41
 */
@Data
public class ConstraintViolationFilterStat {

    /**
     * 过滤出来的违反约束
     */
    private Set<ConstraintViolation<?>> includes;

    /**
     * 未过滤出来的违反约束
     */
    private Set<ConstraintViolation<?>> excludes;


    /**
     * 是否包含过滤出来的违反约束
     *
     * @return true/false
     */
    public boolean hasIncludes() {
        return CollUtil.isNotEmpty(includes);
    }

    /**
     * 是否包含未过滤出来的违反约束
     *
     * @return true/false
     */
    public boolean hasExcludes() {
        return CollUtil.isNotEmpty(excludes);
    }


}
