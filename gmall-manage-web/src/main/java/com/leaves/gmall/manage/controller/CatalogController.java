package com.leaves.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.PmsBaseCatalog1;
import com.leaves.gmall.model.PmsBaseCatalog2;
import com.leaves.gmall.model.PmsBaseCatalog3;
import com.leaves.gmall.service.CatalogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 9:27
 * @Version 1.0
 */

@Controller
@CrossOrigin //允许跨域
public class CatalogController {

    @Reference
    CatalogService catalogService;


    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1() {

        List<PmsBaseCatalog1> catalog1s = catalogService.getCatalog1();

        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {

        List<PmsBaseCatalog2> catalog2s = catalogService.getCatalog2Bycatalog1Id(catalog1Id);

        return catalog2s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {

        List<PmsBaseCatalog3> catalog3s = catalogService.getCatalog3Bycatalog2Id(catalog2Id);

        return catalog3s;
    }

}
