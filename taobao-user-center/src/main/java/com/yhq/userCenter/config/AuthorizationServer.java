package com.yhq.userCenter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 17:28
 */
@Configuration
//开启授权服务器的自动化配置
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    TokenStore tokenStore;
    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    PasswordEncoder encoder;
    @Bean
    AuthorizationServerTokenServices tokenServices() {

        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        //支持刷新token
        services.setSupportRefreshToken(true);
        // token store
        services.setTokenStore(tokenStore);
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }

    /**
     * AuthorizationServerSecurityConfigurer 用来配置令牌端点的安全约束，
     * 也就是这个端点谁能访问，谁不能访问。checkTokenAccess 是指一个 Token 校验的端点，
     * 这个端点我们设置为可以直接访问
     * （在后面，当资源服务器收到 Token 之后，需要去校验 Token 的合法性，就会访问这个端点）。
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * ClientDetailsServiceConfigurer 用来配置客户端的详细信息，
     * 授权服务器要做两方面的检验，一方面是校验客户端，另一方面则是校验用户，校验用户，
     * 我们前面已经配置了，这里就是配置校验客户端。客户端的信息我们可以存在数据库中，
     * 这其实也是比较容易的，和用户信息存到数据库中类似，但是这里为了简化代码，我还是将客
     * 户端信息存在内存中，这里我们分别配置了客户端的 id，secret、资源 id、授权类型、授权范围以及重定向
     * uri。授权类型我在上篇文章中和大家一共讲了四种，四种之中不包含 refresh_token 这种类型，
     * 但是在实际操作中，refresh_token 也被算作一种。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存中的客户端信息
        clients.inMemory()
                .withClient("javaboy")
                .secret(encoder.encode("123"))
                .resourceIds("res1")
                // 密码模式
                .authorizedGrantTypes("password","refresh_token")
//                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("all")
                // 自动跳转的页面
                .redirectUris("http://localhost:8082/index.html");
        // 可以自己配置从别的地方加载客户端信息 例如数据库中。
//        clients.withClientDetails();
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenServices(tokenServices());
    }
}

