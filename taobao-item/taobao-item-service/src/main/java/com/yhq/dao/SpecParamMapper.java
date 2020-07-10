package com.yhq.dao;

import com.yhq.entity.SpecParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 23:43
 */
public interface SpecParamMapper {
    List<SpecParam> list(@Param("gid") Long gid);

    void insert(SpecParam specParam);

    void deleteById(@Param("id") Long id);

    void update(SpecParam specParam);

    List<SpecParam> querySpecParamByCid(@Param("cid") Long cid);

}
