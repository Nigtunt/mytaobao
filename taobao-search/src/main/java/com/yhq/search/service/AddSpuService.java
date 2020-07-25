package com.yhq.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.search.bean.SearchSpu;
import com.yhq.search.bean.Spu;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 17:45
 * 添加商品到es
 */
@Service
public class AddSpuService {
    @Autowired
    RestHighLevelClient client;

    /**
     * 添加商品
     * @param spu
     * @throws IOException
     */
    public void AddSpu(Spu spu) throws IOException {
        IndexRequest request = new IndexRequest("taobao_item_spu","_doc");
        request.id(String.valueOf(spu.getId()));
        SearchSpu searchSpu = new SearchSpu(spu.getTitle(),spu.getSubTitle(),spu.isSaleable());

        request.source(new ObjectMapper().writeValueAsString(searchSpu), XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }
    /**
     * 添加商品
     * @param searchSpuString
     * @throws IOException
     */
    public void AddSpu(String searchSpuString) throws IOException {
        IndexRequest request = new IndexRequest("taobao_item_spu","_doc");

        request.source(searchSpuString, XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    /**
     * 添加多个商品
     * @param spus
     * @throws IOException
     */
    public void AddSpus(List<Spu> spus) throws IOException {
        //  多请求
        BulkRequest request = new BulkRequest();
        for (Spu spu : spus) {
            SearchSpu searchSpu = new SearchSpu(spu.getTitle(),spu.getSubTitle(),spu.isSaleable());
            IndexRequest indexRequest = new IndexRequest("taobao_item_spu","_doc");
            indexRequest.id(String.valueOf(spu.getId()));
            indexRequest.source(new ObjectMapper().writeValueAsString(searchSpu), XContentType.JSON);

            request.add( indexRequest);
        }


        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        client.close();
        System.out.println(response);
    }
}
