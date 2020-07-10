package com.yhq.service;

import com.yhq.dao.CategoryMapper;
import com.yhq.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/2 23:00
 */
@Service
public class CategoryService {
    @Resource
    CategoryMapper mapper;

    public List<Category> findCategoryParentByPid(Long pid){

        return mapper.findCategoryParentByPid(pid);
    }
    public void addCategory(Category category) {
        Long parent_id = category.getParent_id();

        mapper.updateParentId(parent_id);

        Long sort = mapper.getSort(parent_id);
        category.setSort(sort + 1);

        mapper.insertCategory(category);
    }
}
