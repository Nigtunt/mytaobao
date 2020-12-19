import com.yhq.common.util.JsonUtils;
import com.yhq.constant.Cons;
import com.yhq.service.CangkuService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 18:09
 */
public class test2 {
    @Test
    public void test(){
        Connection url = Jsoup
                .connect(Cons.map.get("url")+"api/v1/post/search?search=%E7%99%BE%E5%90%88&page=1&per_page=12&with[]=user&with[]=categories&include=user,categories:simple&simple=1")
                .ignoreContentType(true);
        url.cookies(Cons.cookie);

        try {
            String body = url.execute().body();
            JSONObject object = new JSONObject(body);
            JSONArray data = object.getJSONArray("data");

            System.out.println(data.length());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() throws JSONException {
        List<String > ids = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            ids.add(String.valueOf(i));
        }

        for (String id : ids) {
            sb.append(id).append(",");
        }

        System.out.println(sb.substring(0,sb.length()-1));
    }

    @Test
    public void test3() throws IOException {
        Connection url = Jsoup.connect(Cons.map.get("url") + "/api/v1/post/info?id=" + 182533 + "&include=user,tags,categories,favorited,upvoted").ignoreContentType(true);

        url.cookies(Cons.cookie);
        System.out.println(url.execute().body());
    }

    public static void main(String args[]) throws IOException {

    }
}
