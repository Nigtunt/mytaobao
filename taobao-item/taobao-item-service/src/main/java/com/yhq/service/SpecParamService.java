package com.yhq.service;

import com.yhq.dao.SpecParamMapper;
import com.yhq.entity.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 23:43
 */
@Service
public class SpecParamService {
    @Resource
    private SpecParamMapper specParamMapper;

    public List<SpecParam> specParamList(Long gid) {
        return specParamMapper.list(gid);
    }

    public void insertSpecParam(SpecParam specParam) {

        specParamMapper.insert(specParam);
    }

    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.update(specParam);
    }

    public void deleteSpecParam(Long id) {
        specParamMapper.deleteById(id);
    }

    public List<SpecParam> querySpecParamByCid(Long cid) {
        return specParamMapper.querySpecParamByCid(cid);
    }
}
