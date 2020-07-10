package com.yhq.userCenter.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.userCenter.config.SecurityConfig;
import com.yhq.userCenter.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 19:23
 */
@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    //登录时触发
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        // 从url中拿到 用户名和密码
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        User user = new User(username,password);
        // 生成一个token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        // 验证是否用户名和密码正确
        Authentication authenticate = getAuthenticationManager().authenticate(token);
        logger.info(authenticate.isAuthenticated());
        return authenticate;
    }
    //登录成功时触发，返回一个token
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 拿到 权限
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        StringBuilder as = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            as.append(authority.getAuthority())
                    .append(",");
        }
        String jwt = Jwts.builder()
                .claim("authorities", as)//配置用户角色
                .setSubject(authResult.getName())
                // 设置失效时间
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                // 设置 加密算法 使用私钥加密
                .signWith(SignatureAlgorithm.HS512,"sang@123")
                .compact();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(jwt));
        out.flush();
        out.close();
    }
    //登陆失败时触发
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(SecurityConfig.RespBean.error("登录失败!")));
        out.flush();
        out.close();
    }
}
