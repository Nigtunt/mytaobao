package com.yhq.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Collections;

/**
 * @Author: YHQ
 * @Date: 2020/11/29 13:20
 */
public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
