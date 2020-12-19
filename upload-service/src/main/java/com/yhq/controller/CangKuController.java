package com.yhq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.DO.DetailDO;
import com.yhq.DO.ItemDO;
import com.yhq.constant.Cons;
import com.yhq.mapper.DetailMapper;
import com.yhq.mapper.ItemMapper;
import com.yhq.service.CangkuService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 19:19
 */
@Controller
@RequestMapping("cangku")
@Slf4j
public class CangKuController {

    @Autowired
    CangkuService service;

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    private ItemMapper itemMapper;

    @RequestMapping("bh")
    public String bh(ModelMap model) {
        List<ItemDO> itemDOS = itemMapper.selectList(null);

        if (itemDOS==null||itemDOS.size()==0){
            model.addAttribute("error","错误，未进行缓存或者无最新更新");

            return "bh";
        }
        model.addAttribute("ims", itemDOS.stream().map(item -> new test(item.getThumb(), String.valueOf(item.getDetailId()), item.getTitle())).collect(Collectors.toList()));

        return "bh";
    }

    @RequestMapping("/cacheInstantly")
    @ResponseBody
    public String cacheInstantly()  {
        String realPath = Cons.map.get("realPath");
        return service.Cache(realPath);
    }

    @RequestMapping("item/{id}")
    public String item(@PathVariable String id,Model model) {

        DetailDO detailDO = detailMapper.selectById(id);
        if (detailDO == null){
            model.addAttribute("error","错误页面未缓存");
        }else{
            model.addAttribute("page", detailDO.getContent());
        }
        return "page";

    }

    @GetMapping("/clearNow")
    @ResponseBody
    public String clearNow()  {
        service.clearCache();
        return "success";
    }

}
