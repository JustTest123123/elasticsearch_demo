package muilty;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import test.ref.ConnectElasticsearch;
import test.ref.ElasticsearchTask;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/4/21 下午9:12
 */
public class QueryDoc {
    public static final ElasticsearchTask SEARCH_BY_PAGING = client -> {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 分页查询
        // 当前页其实索引(第一条数据的顺序号)， from
        sourceBuilder.from(0);

        // 每页显示多少条 size
        sourceBuilder.size(2);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    };

    public static void main(String[] args) {
        ConnectElasticsearch.connect(SEARCH_BY_PAGING);
    }

}
    