package com.yhq.userCenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhq.userCenter.dao.ClientMapper;
import com.yhq.userCenter.entity.Client;
import com.yhq.userCenter.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 17:48
 */
@Service
public class ClientAuthService implements ClientDetailsService {
    @Resource
    ClientMapper service;
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        QueryWrapper<Client> ClientId = new QueryWrapper<>();
        ClientId.eq("clientid", s);
        Client client = service.selectOne(ClientId);
        if (client==null) {
            throw new UsernameNotFoundException("User not exists.");
        }
        ClientDetails details = new BaseClientDetails(client.getClientId()
                ,client.getResourceIds(),client.getScopes()
                ,client.getGrantTypes(),client.getAuthorities(),client.getRedirectUris());
//        System.out.println(userDetail);
        return details;
    }
}
