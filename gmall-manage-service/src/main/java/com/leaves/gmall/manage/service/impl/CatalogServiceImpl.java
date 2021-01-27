package com.leaves.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.leaves.gmall.manage.dao.PmsBaseCatalog1Mapper;
import com.leaves.gmall.manage.dao.PmsBaseCatalog2Mapper;
import com.leaves.gmall.manage.dao.PmsBaseCatalog3Mapper;
import com.leaves.gmall.model.PmsBaseCatalog1;
import com.leaves.gmall.model.PmsBaseCatalog2;
import com.leaves.gmall.model.PmsBaseCatalog3;
import com.leaves.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 9:55
 * @Version 1.0
 */

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;


    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2Bycatalog1Id(String catalog1Id) {
        PmsBaseCatalog2  pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        return pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3Bycatalog2Id(String catalog2Id) {
        PmsBaseCatalog3  pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        return pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
    }
}
