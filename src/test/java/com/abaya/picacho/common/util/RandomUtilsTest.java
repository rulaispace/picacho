package com.abaya.picacho.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomUtilsTest {
    @Test
    public void testCreatePassword() {
        System.out.println(RandomUtils.generatePassayPassword());
    }
}