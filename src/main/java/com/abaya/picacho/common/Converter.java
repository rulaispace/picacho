package com.abaya.picacho.common;

public interface Converter<O, T> {
    T get(O origin);
}
