package com.yhq.userCenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.userCenter.Filter.JwtFilter;
import com.yhq.userCenter.Filter.JwtLoginFilter;
import com.yhq.userCenter.service.UserAuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:33
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserAuthService service;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //定义权限，与角色继承
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_dba > ROLE_admin \n ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    //定义角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存中
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123")).roles("admin")
                .and()
                .withUser("sang")
                .password(passwordEncoder().encode("456"))
                .roles("user");
        // 从数据库加载
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    /**
     * 这种配置不走 Spring Security 过滤器链
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web)  {
        web.ignoring().antMatchers("/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico","/verifyCode");
    }
    //定义角色范围
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 这种走过滤链 但设置可以匿名访问
//                .antMatchers("/chart").permitAll()
//                .antMatchers("/hello").hasRole("user")
//                .antMatchers("/admin").hasRole("admin")
//                .antMatchers(HttpMethod.POST, "/login")
//                .permitAll()
//                .antMatchers(HttpMethod.POST, "/register")
//                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtLoginFilter("/login",authenticationManager())
                        , UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable().exceptionHandling()
                // 权限不足是触发
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    RespBean respBean = RespBean.error("访问失败!");
                    if (e instanceof InsufficientAuthenticationException) {
                        respBean.setMsg("请求失败，请联系管理员!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();

                    out.write(new ObjectMapper().writeValueAsString(RespBean.error("权限不足")));
                    out.flush();
                    out.close();
                });
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Data
    public final static class RespBean{
        private String status;
        private String msg;
        private Object o;


        public static RespBean error(String error){
            RespBean re = new RespBean();
            re.msg=error;
            re.status="500";
            return re;
        }
        public static RespBean ok(String error,Object o){
            RespBean re = new RespBean();
            re.msg=error;
            re.status="200";
            re.o=o;
            return re;
        }

    }
}
