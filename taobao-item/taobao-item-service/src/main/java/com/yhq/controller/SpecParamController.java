package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.entity.SpecParam;
import com.yhq.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 23:43
 */
//@RestController
//@RequestMapping("spec")
public class SpecParamController {

//    @Autowired
//    private SpecParamService specParamService;
//    @GetMapping("/params")
//    public ResponseEntity<List<SpecParam>> specParamList(int gid){
//        System.out.println(gid);
//        if (gid <= 0){
//            throw new SystemExecption(ExecptionEnum.ID_NULL);
//        }
//
//        List<SpecParam> list = specParamService.specParamList(gid);
//        if (CollectionUtils.isEmpty(list)){
//            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
//        }
//        return ResponseEntity.ok(list);
//    }

}
