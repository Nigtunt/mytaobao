package com.yhq.service;

import com.yhq.constant.Cons;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 19:35
 */
@Service
@Slf4j
public class CangkuService {

    public String Cache(String fileDocument){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Connection url = Jsoup
                .connect(Cons.map.get("url")+
                        "api/v1/post/search?search=%E7%99%BE%E5%90%88&page=1&per_page=12&with[]=user&with[]=categories&include=user,categories:simple&simple=1")
                .ignoreContentType(true);
        url.cookies(Cons.cookie);
        int count = 0;
        try {
            String body = url.execute().body();
            JSONObject object = new JSONObject(body);
            JSONArray data = object.getJSONArray("data");
            JSONArray newarray = new JSONArray();
            int length = data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                final String thumb = jsonObject.get("thumb").toString();
                for (int i1 = 0; i1 < 10; i1++) {
                    try {
                        Future<Boolean> submit =
                                executorService.submit(() -> cacheImage(thumb, fileDocument));
                        if (submit.get()) break;
                    }catch (Exception e){
                        log.warn("fail" + i1+":"+count);
                    }
                }
                count++;
                int i1 = thumb.lastIndexOf('/');
                String thumb1 = thumb.substring(i1);
                String id = jsonObject.getString("id");
                log.info("开始缓存;"+id);
                cachePage(id,fileDocument + "/"+id+"/");
                newarray.put(i,new JSONObject()
                        .put("id",id)
                        .put("thumb","/cacheImage"+thumb1)
                        .put("title",jsonObject.getString("title"))
                        );
            }
            new FileOutputStream(fileDocument + "/cache.txt").write(newarray.toString().getBytes());
        } catch (IOException | JSONException e) {
            return "未能下载到数据";
        }
        executorService.shutdown();
        return count+"";
    }

    // 缓存具体页面
    public boolean cachePage(String id,String fileDocument){
        File f = new File(fileDocument);
        if (!f.exists()) {
            f.mkdir();
        }else {
            return true;
        }
        String url = Cons.map.get("url")+"/api/v1/post/info?id="+id+"&include=user,tags,categories,favorited,upvoted";
        System.out.println(url);
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            Connection connection = Jsoup.connect(url).timeout(5000).ignoreContentType(true);
            connection.cookies(Cons.cookie);

            Connection.Response execute = connection.execute();
            byte[] bytes = execute.bodyAsBytes();
            JSONObject object = new JSONObject(new String(bytes));
            JSONObject data = object.getJSONObject("data");

            Document content = Jsoup.parse(data.get("content").toString()).normalise();

            Elements img = content.getElementsByTag("img");
            for (Element element : img) {
                final String imgurl = element.attr("src");

                // 缓存图片  格式  /cacheimage + /id + / 图片名
                for (int i1 = 0; i1 < 10; i1++) {
                    try {
                        Future<Boolean> submit = executorService.submit(() -> {
                            try {
                                return cacheImage(imgurl, fileDocument);
                            }catch (Exception e){
                                return false;
                            }
                        });
                        if (submit.get()) break;
                    }catch (Exception e){
                        log.warn("fail" + i1+":");
                    }
                }
                String imgurl2 = imgurl.substring(imgurl.lastIndexOf('/') + 1);
                element.removeAttr("alt");
                element.attr("src","/cacheImage/"+id+"/"+imgurl2);
            }
            FileOutputStream fo = new FileOutputStream(fileDocument + "/cache.txt");

            fo.write(content.body().toString().getBytes());
            return execute.statusCode() == 200;
        }catch (Exception e){
            log.warn(e.getMessage());
            return false;
        }finally {
            executorService.shutdown();
        }

    }


    private boolean cacheImage(String thumb,String fileDocument){
        FileOutputStream fo = null;
        try {
            Connection connect = Jsoup.connect(thumb).timeout(5000).ignoreContentType(true);
            connect.headers(Cons.imageHeader);
            Connection.Response execute = connect.execute();

            byte[] bytes = execute.bodyAsBytes();
            fo = new FileOutputStream(fileDocument + "/"+thumb.substring(1+thumb.lastIndexOf('/')));

            fo.write(bytes);
            if (execute.statusCode() == 200)
                return true;
            else{
                return false;
            }
        } catch (Exception e) {
            return false;
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
