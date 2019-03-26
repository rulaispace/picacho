package com.abaya.picacho.common.util;

import com.abaya.picacho.org.entity.Organization;
import org.junit.Test;

public class PropertyUtilsTest {
    @Test
    public void testMerge() {
        Organization source = new Organization();
        Organization target = new Organization();
        target.setName("天下无敌");

        source.setCode("ABCD");

        target = PropertyUtils.merge(target, source);
        System.out.println(target);
    }

    @Test
    public void testEntityUpdateMerge() {
        Organization source = new Organization();
        Organization target = new Organization();
        target.setName("天下无敌");
        source.setCode("ABCD");

        target.setCreator("TargetCreator");
        source.setCreator("SourceCreator");

        target = EntityUtils.entityUpdateMerge(target, source);
        System.out.println(target);
    }
}