package com.leaves.gmall.service;

import com.leaves.gmall.model.PmsBaseCatalog1;
import com.leaves.gmall.model.PmsBaseCatalog2;
import com.leaves.gmall.model.PmsBaseCatalog3;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 9:52
 * @Version 1.0
 */
public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2Bycatalog1Id(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3Bycatalog2Id(String catalog2Id);
}
