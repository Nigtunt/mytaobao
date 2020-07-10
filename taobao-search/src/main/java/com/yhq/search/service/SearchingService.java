package com.yhq.search.service;

import com.yhq.search.bean.SearchSpu;
import lombok.val;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 17:44
 */
@Service
public class SearchingService {

    @Autowired
    RestHighLevelClient client;

    /**
     * 搜索
     * @param key 关键词
     * @param page 分页
     * @param pageSize 分页
     * @return 返回搜索到的集合
     * @throws IOException
     */
    public List<SearchSpu> search(String key, int page, int pageSize) throws IOException {
        SearchRequest request = new SearchRequest("taobao_item_spu");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(
                QueryBuilders.boolQuery().must(QueryBuilders.termQuery("saleable","true"))
                .should(QueryBuilders.multiMatchQuery(key,"title","sub_title"))
        );
        builder.highlighter(
                new HighlightBuilder().preTags("<font color='red'>")
                .postTags("</font>")
                .field("title")
                .field("sub_title")
        );
//        builder.fetchSource(false);
        request.source(builder);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);


        SearchHit[] hits = search.getHits().getHits();
        List<SearchSpu> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 分数过低直接舍弃
            if(hit.getScore() < 0.01)
                continue;
            // 搜索到的类放到 SearchSpu
            SearchSpu searchSpu = new SearchSpu();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            //更改高亮字段
            HighlightField title = highlightFields.get("title");
            String highlit = getHighlit(title);
            if (highlit.length()!=0){
                searchSpu.setTitle(highlit);
            }else{
                searchSpu.setTitle(sourceAsMap.get("title").toString());
            }

            HighlightField sub_title = highlightFields.get("sub_title");
            String highlit1 = getHighlit(sub_title);
            if (highlit1.length()!=0){
                searchSpu.setSub_title(highlit1);
            }else{
                searchSpu.setSub_title(sourceAsMap.get("sub_title").toString());
            }
            searchSpu.setSaleable((Boolean) sourceAsMap.get("saleable"));
            list.add(searchSpu);
        }

        return list;
    }

    /**
     * 获取高亮的字段
     * @param field
     * @return
     */
    private String getHighlit(HighlightField field) {
        if (field==null) return "";
        Text[] fragments = field.getFragments();
        StringBuilder sb = new StringBuilder();
        for (Text fragment : fragments) {
            sb.append(fragment.string());
        }
        return  sb.toString();
    }
}
