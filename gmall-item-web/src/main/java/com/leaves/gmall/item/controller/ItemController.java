package com.leaves.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.PmsSkuImage;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/28 16:32
 * @Version 1.0
 */

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @RequestMapping(value = "index")
    public String index(ModelMap modelMap) {
        modelMap.put("hello", "hello thymyleaf");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("循环数据" + i);
        }

        modelMap.put("check", 1);
        modelMap.put("list", list);
        return "index";
    }


    @RequestMapping(value = "{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap modelMap) {

        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);



        modelMap.put("skuInfo", pmsSkuInfo);
        return "item";
    }
}
