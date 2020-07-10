package com.yhq.service;

import com.yhq.dao.SpecGroupMapper;
import com.yhq.entity.SpecGroup;
import com.yhq.entity.SpecParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/17 22:59
 */
@Service
public class SpecGroupService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    public List<SpecGroup> specGroupList(int cid) {

        return specGroupMapper.querySpecGroups(cid);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.update(specGroup);
    }

    public void insertSpecGroup(SpecGroup specGroup){
        specGroupMapper.insertSpec(specGroup);
        System.out.println(specGroup.getId());
    }

    public void deleteSpecGroup(int id){
        specGroupMapper.deleteSpec(id);
    }

}
