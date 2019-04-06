package com.abaya.picacho.common.config;

import com.abaya.picacho.common.util.AuthorizeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizeConfig {
    @Bean
    public AuthorizeUtils setApplicationContext() {
        return new AuthorizeUtils();
    }
}
