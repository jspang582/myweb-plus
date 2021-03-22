package cn.sgst.mywebplus.samples.enumsync;

import cn.sgst.mywebplus.core.dict.DictEnumSyncRegistry;
import cn.sgst.mywebplus.core.enums.DynamicEnumUtils;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.samples.serialize.HobbyEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 字典同步枚举测试
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 15:33
 */
public class Main {


    private static DictEnumSyncRegistry registry = DictEnumSyncRegistry.getInstance();

    @BeforeAll
    static void register() {
        registry
                .setProvider(new DefaultDictDetailsProvider())
                .register("sex_type",EnumFactory.SexEnum.class)
                .register("del_type",EnumFactory.StatusEnum.class,new DelEnumSyncProcessor());
    }


    @Test
    void syncSingle() {
        registry.sync("sex_type");
    }

    @Test
    void syncAll() {
        registry.syncAll();
    }

    @Test
    void enumDelAll() {
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
        DynamicEnumUtils.delAllEnum(HobbyEnum.class);
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
        DynamicEnumUtils.addEnum(HobbyEnum.class,"RANDOM",new Class<?>[] {String.class,String.class},new Object[] {"5","watch"});
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
    }
    @Test
    void enumDel() {
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
        DynamicEnumUtils.delEnum(HobbyEnum.class,HobbyEnum.SING.name());
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
    }

    @Test
    void enumUpdate() {
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
        System.out.println(EnumUtils.getTexts(HobbyEnum.class));
        DynamicEnumUtils.updateEnum(HobbyEnum.class,HobbyEnum.SING.name(),new Class<?>[]{String.class,String.class},new Object[] {"1","updateText"});
        System.out.println(EnumUtils.getValues(HobbyEnum.class));
        System.out.println(EnumUtils.getTexts(HobbyEnum.class));
    }
}
