package com.abaya.picacho.common.config;

import com.abaya.picacho.common.util.ConversionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    @Bean
    public ConversionUtils setApplicationContext() {
        return new ConversionUtils();
    }
}
