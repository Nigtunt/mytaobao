package com.yhq.dao;

import com.yhq.entity.Brand;
import com.yhq.vo.PageResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/6/7 19:30
 */
public interface BrandMapper {
    List<Brand> queryBrandByPage(@Param("key") String key);
    int deleteBrandByIds(@Param("ids") Long[] ids);
    void saveBrand(Brand brand);
    void addBrandCategory(@Param("bid") Long bid,@Param("cids") Long[] cids);

    Brand queryBrandById(@Param("id") Long id);

    List<Map<String,String>> queryBrandCategory(@Param("id") Long id);

    void updateBrand(Brand brand);

    List<Brand> queryBrandByCid(@Param("cid")Long cid);
}
