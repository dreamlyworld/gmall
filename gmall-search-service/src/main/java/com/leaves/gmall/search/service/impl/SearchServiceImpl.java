package com.leaves.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.leaves.gmall.model.PmsSkuAttrValue;
import com.leaves.gmall.model.SearchParam;
import com.leaves.gmall.model.SearchSkuInfo;
import com.leaves.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Chenweiwei
 * @Date 2021/2/28 12:18
 * @Version 1.0
 */

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    JestClient jestClient;

    @Override
    public List<SearchSkuInfo> list(SearchParam searchParam) {

        //结果集
        List<SearchSkuInfo> searchSkuInfoList = new ArrayList<>();

        String dslString = getSearchDsl(searchParam);


        System.out.println(dslString);

        //api进行复杂查询
        Search search = new Search.Builder(dslString).addIndex("gmallpms").addType("PmsSkuInfo").build();


        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<SearchSkuInfo, Void>> hits = searchResult.getHits(SearchSkuInfo.class);
        for (SearchResult.Hit<SearchSkuInfo, Void> hit : hits) {
            SearchSkuInfo searchSkuInfo = hit.source;
            Map<String, List<String>> highlight = hit.highlight;
            if (null != highlight) {
                String skuName = highlight.get("skuName").get(0);
                searchSkuInfo.setSkuName(skuName);
            }


            searchSkuInfoList.add(searchSkuInfo);
            System.out.println(searchSkuInfo.toString());
        }

        return searchSkuInfoList;
    }

    private String getSearchDsl(SearchParam searchParam) {

        //参数
        List<String> skuAttrValueIdList = searchParam.getValueId();
        String catalog3Id = searchParam.getCatalog3Id();
        String keyword = searchParam.getKeyword();


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //filter
        if (null != skuAttrValueIdList) {
            for (String valueId : skuAttrValueIdList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", valueId);
                boolQueryBuilder.filter(termQueryBuilder);
            }

        }


        //must
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //query
        sourceBuilder.query(boolQueryBuilder);
        //开始位置
        sourceBuilder.from(0);
        //数量
        sourceBuilder.size(20);
        //排序
        sourceBuilder.sort("id", SortOrder.DESC);
        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //高亮颜色设置
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlight(highlightBuilder);
        //aggs聚合
//        TermsBuilder termsBuilder = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");
//
//        sourceBuilder.aggregation(termsBuilder);

        String dslString = sourceBuilder.toString();

        return dslString;
    }
}
