package com.abaya.picacho.util;

import java.util.UUID;

public class RandomStringUuidUtils {
  public static final String uuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replaceAll("-", "");
  }
}
