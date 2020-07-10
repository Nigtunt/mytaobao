package com.yhq.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhq.dao.BrandMapper;
import com.yhq.dao.CategoryMapper;
import com.yhq.entity.Brand;
import com.yhq.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/6/7 19:32
 */
@Service
@Transactional
public class BrandService {
    @Resource
    BrandMapper brandMapper;
    @Resource
    CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public PageResultVo<Brand> queryBrandByPage(Integer page,Integer pageSize, String sortBy,boolean desc,String key){
        if (sortBy!=null&&sortBy.length()>0){
            if (desc){
                sortBy = sortBy + " desc";
            }
            PageHelper.startPage(page,pageSize,sortBy);
        }else {
            PageHelper.startPage(page,pageSize);
        }

        Page<Brand> brands = (Page<Brand>)brandMapper.queryBrandByPage(key);
        System.out.println(brands);
        return new PageResultVo<>(brands.getTotal(),brands.getResult());
    }

    public int deleteBrand(Long[] ids){
        return brandMapper.deleteBrandByIds(ids);
    }

    public void addBrand(Brand brand, Long[] cids){
        brandMapper.saveBrand(brand);
        brandMapper.addBrandCategory(brand.getId(),cids);
    }

    public Map<String, Object> getBrand(Long id){
        Map<String,Object> map = new HashMap<>();

        map.put("brand",brandMapper.queryBrandById(id));
        List<Map<String, String>> categories = brandMapper.queryBrandCategory(id);
        map.put("categories",categories);
        return map;
    }


    public void updateBrand(Brand brand, Long[] cids) {

        brandMapper.updateBrand(brand);
        //cids 修改 略过
    }

    public List<Brand> queryBrandByCids(Long cid) {

        List<Brand> brands = brandMapper.queryBrandByCid(cid);
        return brands;
    }
}
