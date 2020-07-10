package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/6/2 22:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private Long parent_id;
    private short is_parent;
    private Long sort;
}
