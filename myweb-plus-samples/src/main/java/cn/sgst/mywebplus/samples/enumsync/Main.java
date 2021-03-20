package cn.sgst.mywebplus.samples.enumsync;

import cn.sgst.mywebplus.core.dict.DictEnumSyncRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 15:33
 */
public class Main {


    private static DictEnumSyncRegistry registry = DictEnumSyncRegistry.getInstance();

    @BeforeAll
    static void register() {
        registry.register("sex_type",EnumFactory.SexEnum.class)
                .register("del_type",EnumFactory.StatusEnum.class,new DelEnumSyncProcessor());
    }


    @Test
    void syncSingle() {
        registry.sync("se1x_type");
    }

    @Test
    void syncAll() {
        registry.syncAll();
    }
}
