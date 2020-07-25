package com.yhq.userCenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 17:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String clientId;
    private String resourceIds;
    private String grantTypes;
    private String scopes;
    private String authorities;
    private String redirectUris;
}
