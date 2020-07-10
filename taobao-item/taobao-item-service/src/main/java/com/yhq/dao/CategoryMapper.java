package com.yhq.dao;

import com.yhq.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/2 23:00
 */
public interface CategoryMapper {
    List<Category> findCategoryParentByPid(@Param("pid") Long id);

    void insertCategory(Category category);

    void updateParentId(@Param("pid") Long parent_id);

    Long getSort(@Param("pid") Long pid);

    String queryCategoryById(@Param("id") Long id);

    List<String> queryCategoryByIds(@Param("ids") List<Long> ids);
}
