package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: YHQ
 * @Date: 2020/7/4 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private Long skuId;
    private Long seckillStock;// 秒杀可用库存
    private Long seckillTotal;// 已秒杀数量
    private Long stock;// 正常库存
}
