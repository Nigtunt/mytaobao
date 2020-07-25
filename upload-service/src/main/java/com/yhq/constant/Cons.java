package com.yhq.constant;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 18:35
 */
public class Cons {
    public static Map<String,String> map = new HashMap<>();
    public static Map<String,String> cookie = new HashMap<>();
    public static Map<String,String> imageHeader = new HashMap<>();
    static {
        try {
            File f = new File("/tmp/cacheImage");
            if (!f.exists())
                f.mkdir();
            map.put("realPath", "/tmp/cacheImage");

        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("url","https://cangku.one/");
        setCookie("_ga=GA1.2.2137848297.1589871484; remember_web_59ba36addc2b2f9401580f014c7f58ea4e30989d=eyJpdiI6IjlWSUFITVAwSkxEQVdTYmdRS1UxY2c9PSIsInZhbHVlIjoiZHl5SFM0QXR5cnVrd3NPajhFZHkybTYzS01LQnZEanZlNGRiWUxQOU1hYmFpdG9xaEpQTk56OXFVczE3MXRaM2lEU0p3NVhxSkpVallnQWlQU3liMG9DaUcrbURBVXlObDhkZ1FIOWxMWTJYZ0dzQ0NKVEVNUzJBNlJyRkRETTVBSTBhXC9ZUGpqZHgzR2ZhaGlEU3diRWRMeWJ6anVqQlJvRGRQOUYxXC9UbzhTZW1qMVJncnJWV0pWVkQ0bERnSWMiLCJtYWMiOiI2OWJhZGRiMTc0OTNiYWE3Mzg3NDgyOWFhOGM2NTgxNDlkMTRhMjM4ZDM4NjJjMmZmMjgyMTI3YzQ1OTQ3MDExIn0%3D; _gid=GA1.2.490754732.1595513779; XSRF-TOKEN=eyJpdiI6IkFDUmxrbTUzS01DdzF1V0hqUDJRYXc9PSIsInZhbHVlIjoia1o4VGI5ZlZyM3JCUEhvY0xXUlVoSlRxSUhqK3laQVpHaGk2OVNDNlwveHFCNjFHcWFVWitGU2R3TVdSUmIzRHgiLCJtYWMiOiI0NzM2Mjk4ZDg0ZjQzNmY0MDY2YTExMWJhYTQ2ZjNmMDhhODc3OTI1NDZkNGE4OWQxYjZiNTlkZGE0ODkxODZmIn0%3D; cangku_laravel_session=eyJpdiI6Im9FM2RvZ2o2bFJBMytVU2kyNW1JdXc9PSIsInZhbHVlIjoiU2RJTHNXTm8yaFwvNXFrVlB1VlMxemkzQ1E3cVh0VU1DWWh5Z29KdHg0cUdYTlN3ZDVWSjZpMHhIZUlSWHA4cGwiLCJtYWMiOiIxM2MzYjRlMGU5OWUyY2FmMmU1YjE3YWI5ZWYxZmVkYTdjNjljMmRhN2ViNGRjMGQ4NTVkMTE5ZDYyOTY4Yzc2In0%3D");
        imageHeader.put("accept","image/webp,image/apng,image/*,*/*;q=0.8");
        imageHeader.put("accept-encoding","gzip, deflate, br");
        imageHeader.put("accept-language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        imageHeader.put("cache-control","no-cache");
        imageHeader.put("cookie","_ga=GA1.2.2137848297.1589871484; _gid=GA1.2.490754732.1595513779; PHPSESSID=7os2vuccehin84p99ln1bo3ao4; _gat_gtag_UA_103402143_1=1");
        imageHeader.put("pragma","no-cache");
        imageHeader.put("referer","https://cangku.one/search/post?q=bh");

    }


    public static void setCookie(String cookie){
        Cons.cookie.clear();
        String[] split = cookie.split("; ");
        for (String s : split) {
            String[] split1 = s.split("=");
            Cons.cookie.put(split1[0],split1[1]);
        }
    }

}
