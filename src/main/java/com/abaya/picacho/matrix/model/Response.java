package com.abaya.picacho.matrix.model;

import lombok.Data;

@Data
public class Response<T> {
  public static final int SUCCESS = 200;
  public static final int INNER_ERROR = 501;

  private int status;
  private T payload;

  public Response(int status, T payload) {
    this.status = status;
    this.payload = payload;
  }

  public static Response success() {
    return new Response(SUCCESS, null);
  }

  public static <T> Response<T> success(T payload) {
    return new Response<>(SUCCESS, payload);
  }

  public static Response fail(int status, String message) {
    return new Response(status, message);
  }

  public static Response fail(String message) {
    return new Response(INNER_ERROR, message);
  }
}
