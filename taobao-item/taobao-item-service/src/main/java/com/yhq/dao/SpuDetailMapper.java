package com.yhq.dao;

import com.yhq.entity.SpuBo;
import com.yhq.entity.SpuDetail;
import com.yhq.entity.Stock;

/**
 * @Author: YHQ
 * @Date: 2020/7/4 20:55
 */
public interface SpuDetailMapper{
    void insertSelective(SpuDetail spuBo);

}
