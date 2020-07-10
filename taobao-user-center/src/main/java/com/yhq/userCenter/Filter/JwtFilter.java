package com.yhq.userCenter.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.OpenOption;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 19:10
 */
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        // 拿到jwt
        String jwtToken = req.getHeader("authorization");
        if (jwtToken==null||jwtToken.equals("")){
            filterChain.doFilter(req,servletResponse);
        }else {
            // 获取jwt中信息
            Claims claims = Jwts
                    .parser()
                    // 私钥
                    .setSigningKey("sang@123")
                    //剔除 Bearer
                    .parseClaimsJws(jwtToken.replace("Bearer", ""))
                    .getBody();
            String username = claims.getSubject();//获取当前登录用户名
            //获取当前用户的权限
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
            // 设置token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);

            filterChain.doFilter(req, servletResponse);
        }
    }
}
