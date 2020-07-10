package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/6/7 19:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    private Long id;
    private String name;
    private String image;
    private Character letter;
}
