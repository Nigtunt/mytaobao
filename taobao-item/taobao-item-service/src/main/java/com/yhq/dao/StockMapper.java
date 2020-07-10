package com.yhq.dao;

import com.yhq.entity.Stock;

/**
 * @Author: YHQ
 * @Date: 2020/7/4 20:57
 */
public interface StockMapper {
    void insertSelective(Stock stock);

}
