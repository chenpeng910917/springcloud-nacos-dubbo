package com.springcloud.es;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpeng
 */
@RestController
public class ElasticsearchController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 127.0.0.1:10803/search
     * @return
     */
    @PostMapping("/search")
    public SearchHits<SysUser> search(){
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("money", "10"))
//                .build();

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        return elasticsearchRestTemplate.search(nativeSearchQuery, SysUser.class);
    }

    @PostMapping("/put")
    public SysUser put(SysUser sysUser){
        return elasticsearchRestTemplate.save(sysUser);
    }
}