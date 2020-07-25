package com.yhq.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.PrintWriter;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 14:28
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * token 模式
     */
//    @Bean
//    RemoteTokenServices tokenServices() {
//        RemoteTokenServices services = new RemoteTokenServices();
//        services.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//        services.setClientId("javaboy");
//        services.setClientSecret("123");
//        return services;
//    }
    /**
     * jwt模式直接验证
     */
    @Autowired
    TokenStore tokenStore;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("res1").tokenServices(tokenServices());
        resources.resourceId("res1").tokenStore(tokenStore);
    }
    //资源拦截规则
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/hello/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request,response,e)->{
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    String str = "权限不足";
                    out.write(new ObjectMapper().writeValueAsString(str));
                    out.flush();
                    out.close();
                })
                // 无token
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    String str = "请求失败";
                    if (e instanceof InsufficientAuthenticationException) {
                        str = "请求失败，请联系管理员!";
                    }
                    out.write(new ObjectMapper().writeValueAsString(str));
                    out.flush();
                    out.close();
                })
                ;
    }
}
