package com.yhq.search;

import com.yhq.search.bean.Spu;
import com.yhq.search.service.AddSpuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 16:48
 */
@SpringBootTest
public class test2 {
    @Autowired
    AddSpuService spuService;

    @Test
    public void test(){
        List<Spu> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Spu spu = new Spu();

            spu.setTitle("testTitle"+i);
            spu.setSubTitle("test_sub_title"+i);
            spu.setSaleable(true);
            spu.setId(156166L + i);
            list.add(spu);
        }


        try {
            spuService.AddSpus(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
