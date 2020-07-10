package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int id;
    private String name;
}
