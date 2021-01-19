package cn.sgst.mywebplus.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 非空参数校验类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2019/7/26 10:06
 */
public class ValidateUtil {
    /**
     * 对象是否不为空
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 对象是否为空
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return o.toString().trim().equals("");
        } else if (o instanceof Collection) {
            return ((Collection) o).size() == 0;
        } else if (o instanceof Map) {
            return ((Map) o).size() == 0;
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else if (o instanceof int[]) {
            return ((int[]) o).length == 0;
        } else if (o instanceof long[]) {
            return ((long[]) o).length == 0;
        } else if (o instanceof double[]) {
            return ((double[]) o).length == 0;
        } else if (o instanceof byte[]) {
            return ((byte[]) o).length == 0;
        } else if (o instanceof float[]) {
            return ((float[]) o).length == 0;
        } else if (o instanceof boolean[]) {
            return ((boolean[]) o).length == 0;
        } else if (o instanceof char[]) {
            return ((char[]) o).length == 0;
        } else if (o instanceof short[]) {
            return ((short[]) o).length == 0;
        }
        return false;
    }


    /**
     * 对象组中是否存在空对象
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是空对象
     */
    public static boolean isAllEmpty(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }
}
