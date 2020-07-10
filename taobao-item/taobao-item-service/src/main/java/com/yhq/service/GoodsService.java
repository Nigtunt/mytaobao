package com.yhq.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yhq.dao.*;
import com.yhq.entity.Spu;
import com.yhq.entity.SpuBo;
import com.yhq.entity.SpuDetail;
import com.yhq.entity.Stock;
import com.yhq.vo.PageResultVo;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YHQ
 * @Date: 2020/6/19 14:33
 */
@Service
public class GoodsService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private SpuDetailMapper spuDetailMapper;
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private StockMapper stockMapper;

    @Resource
    private BrandMapper brandMapper;

    public PageResultVo<Spu> querySpuByPage(int page,int rows, Boolean saleable,String key){
        PageHelper.startPage(page,rows);

        Page<Spu> pageInfo = (Page<Spu>)spuMapper.selectSpu(saleable,key);

        List<Spu> collect = pageInfo.getResult().stream().peek(spu -> {
            List<String> strings = categoryMapper.queryCategoryByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())
            );
            spu.setCName(StringUtils.join(strings, '/'));

            spu.setBName(brandMapper.queryBrandById(spu.getBrandId()).getName());

        }).collect(Collectors.toList());
        return new PageResultVo<>(pageInfo.getTotal(),collect);
    }
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(true);
        spuBo.setSaleable(true);
        //添加spu
        spuMapper.insertSelective(spuBo);
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        //添加spudetail
        spuDetailMapper.insertSelective(spuBo.getSpuDetail());
        //sku
        spuBo.getSkus().forEach((e)->{
            e.setCreateTime(spuBo.getCreateTime());
            e.setLastUpdateTime(e.getCreateTime());
            e.setSpuId(spuBo.getId());
            skuMapper.insertSelective(e);

            Stock stock = new Stock();
            stock.setStock(e.getStock());
            stock.setSkuId(e.getId());

            stockMapper.insertSelective(stock);
        });
        //添加stock

    }
}
