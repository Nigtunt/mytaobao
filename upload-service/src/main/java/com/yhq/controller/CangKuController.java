package com.yhq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.constant.Cons;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("bh")
    public String bh(ModelMap model) {
        String realPath = Cons.map.get("realPath");
        if (realPath==null||realPath.length()==0){
            model.addAttribute("error","no realPath");
            return "bh";
        }
        File file = new File(realPath+"/cache.txt");
        if (!file.exists()){
            model.addAttribute("error","错误，未进行缓存");
            return "bh";
        }
        try {
            FileReader fo = new FileReader(file);
            char c[] = new char[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = fo.read(c))!=-1){
                sb.append(new String(c,0,len));
            }
            String s = sb.toString();
            JSONArray jsonArray = new JSONArray(s);
            ArrayList<test> list = new ArrayList<>();
            len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                if (jsonArray.isNull(i)) {
                    list.add(new test("thumb","id","title"));
                }else{
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(new test(jsonObject.getString("thumb"),jsonObject.getString("id"),jsonObject.getString("title")));
                }

            }
            model.addAttribute("ims",list);
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        File f = new File(Cons.map.get("realPath")+"/"+id);
        if (!f.exists()){
            model.addAttribute("error","错误页面未缓存");
        }else{
            try {
                FileInputStream fi = new FileInputStream(f.getPath() + "/cache.txt");
                int available = fi.available();
                byte b[] = new byte[available];
                fi.read(b);
                model.addAttribute("page",new String(b));
            } catch (IOException e) {
                model.addAttribute("error","内部错误(可能是缓存失败)");
            }
        }
        return "page";

    }

}
