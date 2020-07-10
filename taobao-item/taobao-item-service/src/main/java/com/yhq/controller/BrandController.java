package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.entity.Brand;
import com.yhq.service.BrandService;
import com.yhq.vo.PageResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/6/7 19:23
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;
    @GetMapping("page")
    public ResponseEntity<PageResultVo<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){

        PageResultVo<Brand> pageInfo = brandService.queryBrandByPage(page, pageSize, sortBy, desc, key);
        if (pageInfo==null||pageInfo.getItems().size()==0){
            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
        }
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping("delete")
    public ResponseEntity<Integer> delete(Long[] ids){
        if (ids==null || ids.length==0){
            throw new SystemExecption(ExecptionEnum.RESULT_NULL);
        }

        int i = brandService.deleteBrand(ids);

        return ResponseEntity.ok(i);
    }

    @PostMapping("save")
    public ResponseEntity<Void> save(
        Brand brand,
        Long[] cids,
        String mode
    ){
        System.out.println(brand);
        System.out.println(Arrays.deepToString(cids));
        if (mode.equals("add")){
            brandService.addBrand(brand , cids);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else if (mode.equals("update")){
            brandService.updateBrand(brand, cids);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("queryBrandById")
    public ResponseEntity<Map<String,Object>> queryBrandById(Long id){

        if (id==null||id==0){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }

        return ResponseEntity.ok(brandService.getBrand(id));
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandBiCid(@PathVariable Long cid){
        List<Brand> brands = brandService.queryBrandByCids(cid);

        if (CollectionUtils.isEmpty(brands)){
            throw new SystemExecption(ExecptionEnum.ID_NULL);
        }

        return ResponseEntity.ok(brands);
    }
}
