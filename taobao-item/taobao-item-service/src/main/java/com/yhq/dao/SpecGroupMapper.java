package com.yhq.dao;

import com.yhq.entity.SpecGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/17 22:59
 */
public interface SpecGroupMapper {

    List<SpecGroup> querySpecGroups(@Param("cid") int cid);

    void update(SpecGroup specGroup);

    void insertSpec(SpecGroup specGroup);

    void deleteSpec(@Param("id") int id);
}
