package cn.sgst.mywebplus.core.convert;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.XmlUtil;
import cn.sgst.mywebplus.core.enums.IEnum;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * 扩展{@link cn.hutool.core.convert.impl.StringConverter}的转换规则
 * <p>提供集合转字符串的方法,将集合元素以","隔开</p>
 * <p>提供实现IEnum的枚举转字符串的方法,将集合元素以","隔开</p>
 * <p>
 * 如果元素是实现了IEnum接口的枚举类,则将调用getValue()方法
 * 如果不是,则调用默认的toString()方法
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/4/7 11:28
 */
public class HutoolStringConverter extends cn.hutool.core.convert.impl.StringConverter {

    @Override
    @SuppressWarnings("unchecked")
    protected String convertInternal(Object value) {
        if (null == value) {
            return null;
        }
        if (isIEnum(value)) {
            return ((IEnum) value).getValue().toString();
        }
        if (value instanceof TimeZone) {
            return ((TimeZone) value).getID();
        } else if (value instanceof org.w3c.dom.Node) {
            return XmlUtil.toStr((org.w3c.dom.Node) value);
        } else if (value instanceof Clob) {
            return clobToStr((Clob) value);
        } else if (value instanceof Blob) {
            return blobToStr((Blob) value);
        }
        if (value instanceof CharSequence) {
            return value.toString();
        } else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).stream().map(o -> {
                String val;
                if (isIEnum(o)) {
                    val = ((IEnum) o).getValue().toString();
                } else {
                    val = o.toString();
                }
                return val;
            }).collect(Collectors.joining(","));
        } else if (ArrayUtil.isArray(value)) {
            return ArrayUtil.toString(value);
        } else if (CharUtil.isChar(value)) {
            //对于ASCII字符使用缓存加速转换，减少空间创建
            return CharUtil.toString((char) value);
        }
        // 其它
        return value.toString();
    }

    /**
     * 判断是不是实现了IEnum的枚举类
     *
     * @param value 值
     * @return 布尔值
     */
    private static boolean isIEnum(Object value) {
        if (value == null) {
            return false;
        }
        Class<?> clazz = value.getClass();
        return clazz.isEnum() && IEnum.class.isAssignableFrom(clazz);
    }

    /**
     * Clob字段值转字符串
     *
     * @param clob {@link Clob}
     * @return 字符串
     * @since 5.4.5
     */
    private static String clobToStr(Clob clob) {
        Reader reader = null;
        try {
            reader = clob.getCharacterStream();
            return IoUtil.read(reader);
        } catch (SQLException e) {
            throw new ConvertException(e);
        } finally {
            IoUtil.close(reader);
        }
    }

    /**
     * Blob字段值转字符串
     *
     * @param blob {@link Blob}
     * @return 字符串
     * @since 5.4.5
     */
    private static String blobToStr(Blob blob) {
        InputStream in = null;
        try {
            in = blob.getBinaryStream();
            return IoUtil.read(in, CharsetUtil.CHARSET_UTF_8);
        } catch (SQLException e) {
            throw new ConvertException(e);
        } finally {
            IoUtil.close(in);
        }
    }
}
