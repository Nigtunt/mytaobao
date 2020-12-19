import com.yhq.constant.Cons;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: YHQ
 * @Date: 2020/8/9 16:25
 */
public class ThreadTest {
    public static void main(String args[])  {
        System.out.println("pre");
        try {
            for (int i = 1003; i < 1500; i++) {
                page(i);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    private static void page(int i) {
        String url = "https://cangku.one/api/v1/post/list?page="+i+"&per_page=18&with[]=user&with[]=categories&include=user,categories:simple&simple=1";

        try {
            byte[] bytes = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .cookies(Cons.cookie)
                    .execute().bodyAsBytes();
            JSONObject object = new JSONObject(new String(bytes));

            JSONArray data = object.getJSONArray("data");

            for (int j = 0; j < data.length(); j++) {
                String thumb = data.getJSONObject(j).getString("thumb");

                try {
                    stored(thumb);
                } catch (Throwable e) {
                    System.out.println("store thumb fail : " + thumb);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void stored(String thumb) throws IOException {
        String filename = thumb.substring(thumb.lastIndexOf('/'));
        Connection connect = Jsoup.connect(thumb).ignoreContentType(true);
        connect.headers(map);
        byte[] bytes = connect.execute().bodyAsBytes();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\BaiduYunDownload\\test\\" + filename);

        fileOutputStream.write(bytes);

        fileOutputStream.close();
        System.out.println(filename);
    }
    private static Map<String,String> map ;

    static {
        map = new HashMap<>();

        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Connection","keep-alive");
        map.put("referer","https://cangku.one/?page=1002");
        map.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36 Edg/84.0.522.52");
    }
}
