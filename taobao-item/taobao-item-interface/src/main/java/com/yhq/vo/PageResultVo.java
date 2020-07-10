package com.yhq.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/7 19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResultVo<T> {
    private long total;
    private List<T> items;
}
