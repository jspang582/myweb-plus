package cn.sgst.mywebplus.core.enums;

import cn.hutool.core.util.EnumUtil;
import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 动态操作枚举工具类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/1 17:03
 */
@SuppressWarnings("all")
public class DynamicEnumUtils {


    private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException,
            IllegalAccessException {

        // 反射访问私有变量
        field.setAccessible(true);

        /*
         * 接下来，我们将字段实例中的修饰符更改为不再是final，
         * 从而使反射允许我们修改静态final字段。
         */
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // 去掉修饰符int中的最后一位
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(target, value);
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException,
            IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[]{field}, true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstantDirectory");
        // IBM JDK
        blankField(enumClass, "enumConstants");
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes)
            throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static Object makeEnum(Class<?> enumClass, String value, int ordinal, Class<?>[] additionalTypes,
                                   Object[] additionalValues) throws Exception {
        Object[] parms = new Object[additionalValues.length + 2];
        parms[0] = value;
        parms[1] = Integer.valueOf(ordinal);
        System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(parms));
    }

    /**
     * 将枚举实例添加到作为参数提供的枚举类中
     *
     * @param enumType         要修改的枚举类型
     * @param enumName         添加的枚举类型名字
     * @param additionalTypes  枚举类型参数类型列表
     * @param additionalValues 枚举类型参数值列表
     * @param <T>              泛型
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> void addEnum(Class<T> enumType, String enumName, Class<?>[] additionalTypes, Object[] additionalValues) {

        // 0. 检查类型
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 1. 在枚举类中查找“$values”持有者并获取以前的枚举实例
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[]{valuesField}, true);

        try {
            // 2. 将他拷贝到数组
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. 创建新的枚举项
            T newValue = (T) makeEnum(enumType, enumName, values.size(), additionalTypes, additionalValues);

            // 4. 添加新的枚举项
            values.add(newValue);

            // 5. 设定拷贝的数组，到枚举类型
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. 清除枚举的缓存
            cleanEnumCache(enumType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 删除所有的枚举值
     *
     * @param enumType 要修改的枚举类型
     * @param <T>      泛型
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> void delAllEnum(Class<T> enumType) {
        // 0. 检查类型
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 1. 在枚举类中查找“$values”持有者并获取以前的枚举实例
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[]{valuesField}, true);
        try {
            List<T> values = new ArrayList<T>();
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));
            // 2.清除枚举的缓存
            cleanEnumCache(enumType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 删除枚举值
     *
     * @param enumType 枚举类型
     * @param enumName 删除的枚举类型名字
     * @param <T>      泛型
     */
    public static <T extends Enum<?>> void delEnum(Class<T> enumType, String enumName) {
        // 0. 检查类型
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }
        // 判断是否有该枚举名,如果没有则直接返回
        List<String> names = EnumUtil.getNames(enumType);
        if (!names.contains(enumName)) {
            return;
        }
        // 1. 在枚举类中查找“$values”持有者并获取以前的枚举实例
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[]{valuesField}, true);

        try {
            // 2. 将他拷贝到数组
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));
            // 3. 移除指定的枚举值
            values.removeIf(value -> value.name().equals(enumName));
            // 4. 设定拷贝的数组，到枚举类型
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));
            // 5. 清除枚举的缓存
            cleanEnumCache(enumType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 更新枚举值
     *
     * @param enumType         枚举类型
     * @param enumName         更新的枚举类型名字
     * @param additionalTypes  枚举类型参数类型列表
     * @param additionalValues 枚举类型参数值列表
     * @param <T>              泛型
     */
    public static <T extends Enum<?>> void updateEnum(Class<T> enumType, String enumName, Class<?>[] additionalTypes, Object[] additionalValues) {
        // 0. 检查类型
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }
        // 判断是否有该枚举名,如果没有则直接返回
        List<String> names = EnumUtil.getNames(enumType);
        if (!names.contains(enumName)) {
            return;
        }
        // 先删除
        delEnum(enumType, enumName);

        // 1. 在枚举类中查找“$values”持有者并获取以前的枚举实例
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[]{valuesField}, true);

        try {
            // 2. 将他拷贝到数组
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. 再创建新的枚举项
            T newValue = (T) makeEnum(enumType, enumName, values.size(), additionalTypes, additionalValues);

            // 4. 添加新的枚举项
            values.add(names.indexOf(enumName),newValue);

            // 5. 设定拷贝的数组，到枚举类型
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. 清除枚举的缓存
            cleanEnumCache(enumType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
