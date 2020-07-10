package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 23:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecParam {
    private int id;
    private String name;
    private int cid;
    private int group_id;
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
