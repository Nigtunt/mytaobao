package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.entity.SpecGroup;
import com.yhq.entity.SpecParam;
import com.yhq.service.SpecGroupService;
import com.yhq.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: YHQ
 * @Date: 2020/6/17 22:58
 */
@RestController
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    private SpecGroupService specGroupService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> specGroupList(@PathVariable int cid){
        if (cid <= 0){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }
        List<SpecGroup> specGroups = specGroupService.specGroupList(cid);
        if (CollectionUtils.isEmpty(specGroups)){
            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
        }
        return ResponseEntity.ok(specGroups);
    }

    @PutMapping("/group")
    public ResponseEntity<String> EditGroup(@RequestBody SpecGroup specGroup){
        if (specGroup==null|| StringUtils.isEmpty(specGroup.getName())){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }
        specGroupService.updateSpecGroup(specGroup);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/group")
    public ResponseEntity<String> addGroup(@RequestBody SpecGroup specGroup){
        if (specGroup==null|| StringUtils.isEmpty(specGroup.getName())){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }
        specGroupService.insertSpecGroup(specGroup);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/group/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable int id){

        specGroupService.deleteSpecGroup(id);
        return ResponseEntity.ok("success");
    }

    @Autowired
    private SpecParamService specParamService;
    @GetMapping("/param")
    public ResponseEntity<List<SpecParam>> specParamList(Long gid){
        if (gid <= 0){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }

        List<SpecParam> list = specParamService.specParamList(gid);
        if (CollectionUtils.isEmpty(list)){
            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping("/param")
    public ResponseEntity<String> addSpecParams(@RequestBody SpecParam specParam){
        if (specParam==null|| StringUtils.isEmpty(specParam.getName())){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }
        specParamService.insertSpecParam(specParam);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/param/{id}")
    public ResponseEntity<String> deleteSpecParam(@PathVariable Long id){
        specParamService.deleteSpecParam(id);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/param")
    public ResponseEntity<String> editSpecParams(@RequestBody SpecParam specParam){
        if (specParam==null|| StringUtils.isEmpty(specParam.getName())){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }
        specParamService.updateSpecParam(specParam);

        return ResponseEntity.ok("success");
    }
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParamByCid(Long cid){
        if (cid <= 0){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }

        List<SpecParam> list = specParamService.querySpecParamByCid(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
        }
        CyclicBarrier c = new CyclicBarrier(2);

        return ResponseEntity.ok(list);
    }
}
