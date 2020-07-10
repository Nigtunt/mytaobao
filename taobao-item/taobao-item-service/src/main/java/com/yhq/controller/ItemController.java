package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: YHQ
 * @Date: 2020/5/30 11:22
 */
@RestController
public class ItemController {
    @GetMapping("/getItem")
    public Item getItem(){
        return new Item(1,"item");
    }

    @PostMapping("/saveItem")
    public ResponseEntity<Item> save(Item item){
        if (item.getName()==null){
            throw new SystemExecption(ExecptionEnum.NULL_PRICE_EXECPTION);
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(item);
    }

}
