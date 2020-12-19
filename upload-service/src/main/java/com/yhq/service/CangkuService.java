package com.yhq.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhq.DO.DetailDO;
import com.yhq.DO.ItemDO;
import com.yhq.constant.Cons;
import com.yhq.mapper.DetailMapper;
import com.yhq.mapper.ItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 19:35
 */
@Service
@Slf4j
public class CangkuService {
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    private ItemMapper itemMapper;

    public String Cache(String fileDocument){
        Connection url = Jsoup
                .connect(Cons.map.get("url")+
                        "api/v1/post/search?search=%E7%99%BE%E5%90%88&page=1&per_page=12&with[]=user&with[]=categories&include=user,categories:simple&simple=1")
                .ignoreContentType(true);
        url.cookies(Cons.cookie);

        List<Integer> ids = itemMapper.selectList(null).stream().map(ItemDO::getDetailId).collect(Collectors.toList());

        int count = 0;
        try {
            String body = url.execute().body();
            JSONObject object = new JSONObject(body);
            JSONArray data = object.getJSONArray("data");

            int length = data.length();
            List<Future<String>> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                Integer id = jsonObject.getInt("id");
                if (ids.contains(id)){
                    continue;
                }
                final String thumb = jsonObject.get("thumb").toString();
                Future<String> submit = submitTask(thumb, fileDocument);
                list.add(submit);
            }
            List<String> res = new ArrayList<>();
            int t = 0;
            for (Future<String> future : list) {
                if (future.get().equals("success")) {
                    t++;
                }
                res.add(future.get());
            }
            log.info("封面图片一共：{}，成功：{}，{} ", length, t, res);

            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                Integer id = jsonObject.getInt("id");
                if (ids.contains(id)){
                    continue;
                }
                final String thumb = jsonObject.get("thumb").toString();

                count++;
                int i1 = thumb.lastIndexOf('/');
                String thumb1 = thumb.substring(i1);
                log.info("开始缓存;"+id);
                cachePage(id,fileDocument + "/"+id+"/");
                ItemDO title = new ItemDO().setDetailId(id).setThumb("/cacheImage" + thumb1).setTitle(jsonObject.getString("title"));

                itemMapper.insert(title);
                log.info("{} 缓存完成, content：{}", id, title);
            }

        } catch (Exception e) {
            return "未能下载到数据";
        }

        return String.valueOf(count);
    }

    // 缓存具体页面
    public boolean cachePage(Integer id,String fileDocument){
        File f = new File(fileDocument);
        if (!f.exists()) {
            f.mkdir();
        }else {
            return true;
        }

        String url = Cons.map.get("url")+"/api/v1/post/info?id="+id+"&include=user,tags,categories,favorited,upvoted";
        try {
            Connection connection = Jsoup.connect(url).timeout(5000).ignoreContentType(true);
            connection.cookies(Cons.cookie);

            Connection.Response execute = connection.execute();
            byte[] bytes = execute.bodyAsBytes();
            JSONObject object = new JSONObject(new String(bytes));
            JSONObject data = object.getJSONObject("data");

            Document content = Jsoup.parse(data.get("content").toString()).normalise();
            entity(content, id);
            Elements img = content.getElementsByTag("img");
            int count = 0;
            List<Future<String>> list = new ArrayList<>();
            for (Element element : img) {
                if (count++>10) {
                    break;
                }
                final String imgurl = element.attr("src");

                // 缓存图片  格式  /cacheimage + /id + / 图片名
                Future<String> submit = submitTask(imgurl, fileDocument);
                list.add(submit);
            }
            List<String> res = new ArrayList<>();
            int t = 0;
            for (Future<String> future : list) {
                if (future.get().equals("success")) {
                    t++;
                }
                res.add(future.get());
            }
            log.info("图片一共：{}，成功：{}，{} ", count, t, res);
            return execute.statusCode() == 200;
        }catch (Exception e){
            return false;
        }
    }

    private Future<String> submitTask(String imgurl, String fileDocument) {
        return executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    if ("success".equals(cacheImage(imgurl, fileDocument)))
                        return "success";
                }catch (Throwable e){
                    log.info("{}, {}, fail {}",e.getMessage(), imgurl, i);
                }
            }
            return "fail";
        });
    }

    private void entity(Document content, int id) {
        Document clone = content.clone();
        Elements elements = clone.getElementsByTag("img");
        for (Element element : elements) {
            final String imgurl = element.attr("src");
            String imgurl2 = imgurl.substring(imgurl.lastIndexOf('/') + 1);
            element.removeAttr("alt");
            element.attr("src","/cacheImage/"+id+"/"+imgurl2);
        }
        detailMapper.insert(new DetailDO().setId(id).setContent(clone.body().toString()));

    }


    private String cacheImage(String thumb,String fileDocument){
        FileOutputStream fo = null;
        try {
            Connection connect = Jsoup.connect(thumb).timeout(5000).ignoreContentType(true);
            connect.headers(Cons.imageHeader);
            Connection.Response execute = connect.execute();

            byte[] bytes = execute.bodyAsBytes();
            fo = new FileOutputStream(fileDocument + "/"+thumb.substring(1+thumb.lastIndexOf('/')));

            fo.write(bytes);
            if (execute.statusCode() == 200)
                return "success";
            else{
                return thumb;
            }
        } catch (Exception e) {
            return thumb;
        }finally {
            try {
                if (fo != null) {
                    fo.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearCache() {
        File file = new File(Cons.map.get("realPath"));
        File[] files = file.listFiles();
        if (files!=null)
            for (File file1 : files) {
                delFile(file1);
            }
    }
    boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files) {
                    delFile(f);
                }
        }
        return file.delete();
    }
}
