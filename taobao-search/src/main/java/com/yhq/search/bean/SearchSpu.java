package com.yhq.search.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 17:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSpu {
    private String title;
    private String sub_title;
    private boolean saleable;
}
