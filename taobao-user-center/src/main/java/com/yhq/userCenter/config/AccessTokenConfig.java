package com.yhq.userCenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 14:12
 */
@Configuration
public class AccessTokenConfig {
    private String SIGNING_KEY = "javaboy";

    @Bean
    TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 另外我们还提供了一个 JwtAccessTokenConverter，这个 JwtAccessTokenConverter
     * 可以实现将用户信息和 JWT 进行转换（将用户信息转为 jwt 字符串，或者从 jwt 字符串提取出用户信息）。
     * 另外，在 JWT 字符串生成的时候，我们需要一个签名，这个签名需要自己保存好。
     * @return
     */
    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomAdditionalInformation();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
