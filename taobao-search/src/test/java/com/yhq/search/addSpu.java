package com.yhq.search;

import com.yhq.search.bean.Spu;
import com.yhq.search.dao.SpuMapper;
import com.yhq.search.service.AddSpuService;
import com.yhq.search.service.SearchingService;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 18:25
 */
@SpringBootTest
public class addSpu {
    @Autowired
    AddSpuService spuService;

    @Autowired
    SearchingService searchingService;
    @Autowired
    SpuMapper spuMapper;

    @Test
    public void test() throws IOException {

        List<Spu> spus = spuMapper.selectList(null);
        spuService.AddSpus(spus);
    }

    @Test
    public void test2() throws IOException {
        searchingService.search("华为", 0, 0).forEach(System.out::println);
    }
}
