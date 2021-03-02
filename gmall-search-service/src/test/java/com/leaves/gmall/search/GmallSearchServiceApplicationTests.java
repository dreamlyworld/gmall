package com.leaves.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.model.SearchSkuInfo;
import com.leaves.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.catalina.LifecycleState;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GmallSearchServiceApplicationTests {

    @Reference
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //filter
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","39");
        boolQueryBuilder.filter(termQueryBuilder);

        //must
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","移动");
        boolQueryBuilder.must(matchQueryBuilder);

        //query
        sourceBuilder.query(boolQueryBuilder);

        String dslString = sourceBuilder.toString();

        System.out.println(dslString);


        //api进行复杂查询
        Search search = new Search.Builder(dslString).addIndex("gmallpms").addType("PmsSkuInfo").build();


        SearchResult searchResult = jestClient.execute(search);

        List<SearchResult.Hit<SearchSkuInfo, Void>> hits = searchResult.getHits(SearchSkuInfo.class);
        for (SearchResult.Hit<SearchSkuInfo, Void> hit : hits) {
            SearchSkuInfo searchSkuInfo = hit.source;
            System.out.println(searchSkuInfo.toString());
        }

    }

    @Test
    public void put() throws IOException {
        List<PmsSkuInfo> pmsSkuInfoList = new ArrayList<>();

        pmsSkuInfoList = skuService.getAllSkuInfo();

        List<SearchSkuInfo> searchSkuInfoList = new ArrayList<>();


        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            SearchSkuInfo searchSkuInfo = new SearchSkuInfo();
            BeanUtils.copyProperties(pmsSkuInfo, searchSkuInfo);
            searchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));

            searchSkuInfoList.add(searchSkuInfo);
        }


        for (SearchSkuInfo searchSkuInfo : searchSkuInfoList) {
            Index put = new Index.Builder(searchSkuInfo).index("gmallpms").type("PmsSkuInfo").id(String.valueOf(searchSkuInfo.getId())).build();
            jestClient.execute(put);
        }


    }
}
