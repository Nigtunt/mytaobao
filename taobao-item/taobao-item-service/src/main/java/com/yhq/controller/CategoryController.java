package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.entity.Category;
import com.yhq.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: YHQ
 * @Date: 2020/6/2 23:17
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByParentId(Long pid){
        if (pid==null||pid < 0){
//            return ResponseEntity.badRequest().build();
            throw new SystemExecption(ExecptionEnum.PID_CANT_NULL);
        }

        List<Category> categoryParentByPid = categoryService.findCategoryParentByPid(pid);

        if (CollectionUtils.isEmpty(categoryParentByPid)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryParentByPid);
    }

    @PostMapping("add")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        if (category==null||category.getName()==null){
            throw new SystemExecption(ExecptionEnum.CATEGORY_NULL);
        }
        try {
            categoryService.addCategory(category);
        }catch (Exception e){
            return ResponseEntity.ok("fail");
        }
        return ResponseEntity.ok("success");
    }
}
