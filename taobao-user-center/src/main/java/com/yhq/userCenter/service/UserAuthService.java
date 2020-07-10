package com.yhq.userCenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhq.userCenter.dao.UserMapper;
import com.yhq.userCenter.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:53
 */
@Service
public class UserAuthService implements UserDetailsService {
    @Resource
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> username = new QueryWrapper<>();
        username.eq("username", s);
        User user = userMapper.selectOne(username);
        if (user==null) {
            throw new UsernameNotFoundException("User not exists.");
        }
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_user"));
        UserDetails userDetail = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authList);
//        System.out.println(userDetail);
        return userDetail;
    }
}
