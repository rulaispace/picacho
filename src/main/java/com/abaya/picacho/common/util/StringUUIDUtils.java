package com.abaya.picacho.common.util;

import java.util.UUID;

public class StringUUIDUtils {
  public static final String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
