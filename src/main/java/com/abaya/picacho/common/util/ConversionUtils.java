package com.abaya.picacho.common.util;

import com.abaya.picacho.common.convert.ExtensibleEntityConverter;
import com.abaya.picacho.common.convert.GenericConverter;
import com.abaya.picacho.common.convert.NothingConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.DefaultConversionService;

@Slf4j
public class ConversionUtils {
    private static final DefaultConversionService conversionService;
    static {
        conversionService = new DefaultConversionService();
        conversionService.addConverter(new NothingConverter());
        conversionService.addConverter(new ExtensibleEntityConverter());
        conversionService.addConverter(new GenericConverter());
    }

    public static <T> T convert(Object source, Class<T> type) {
        return EntityUtils.overrideOperator(source, conversionService.convert(source, type));
    }
}
