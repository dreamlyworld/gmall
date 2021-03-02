package com.leaves.gmall.service;

import com.leaves.gmall.model.SearchParam;
import com.leaves.gmall.model.SearchSkuInfo;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/2/28 12:13
 * @Version 1.0
 */
public interface SearchService {
    List<SearchSkuInfo> list(SearchParam searchParam);
}
