package com.yhq.controller;

import com.yhq.entity.Spu;
import com.yhq.entity.SpuBo;
import com.yhq.service.GoodsService;
import com.yhq.vo.PageResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: YHQ
 * @Date: 2020/6/19 14:34
 */
@RestController
@RequestMapping("/spu")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/page")
    public ResponseEntity<PageResultVo<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key) {
        // 分页查询spu信息
        PageResultVo<Spu> result = this.goodsService.querySpuByPage(page, rows,saleable, key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("save")
    public ResponseEntity<Void> save(@RequestBody SpuBo spuBo){
        System.out.println(spuBo);
        goodsService.saveGoods(spuBo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
