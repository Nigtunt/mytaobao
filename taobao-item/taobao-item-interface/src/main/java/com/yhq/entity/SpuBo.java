package com.yhq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/4 18:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpuBo extends Spu {

    String cname;// 商品分类名称
    String bname;// 品牌名称
    SpuDetail spuDetail;// 商品详情
    List<Sku> skus;// sku列表
}
