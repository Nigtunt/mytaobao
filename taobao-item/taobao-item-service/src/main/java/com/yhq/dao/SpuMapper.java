package com.yhq.dao;

import com.yhq.config.RedisCache;
import com.yhq.entity.Spu;
import com.yhq.entity.SpuBo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/19 14:33
 */
// 缓存
//@CacheNamespace(implementation = RedisCache.class)
public interface SpuMapper{
    List<Spu> selectSpu(@Param("saleable") Boolean saleable,@Param("key") String key);


    void insertSelective(SpuBo spuBo);
}
