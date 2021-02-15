package com.leaves.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.leaves.gmall.model.PmsProductSaleAttr;
import com.leaves.gmall.model.PmsSkuImage;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.model.PmsSkuSaleAttrValue;
import com.leaves.gmall.service.SkuService;
import com.leaves.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Chenweiwei
 * @Date 2021/1/28 16:32
 * @Version 1.0
 */

@Controller
public class ItemController {

    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

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
    public String item(@PathVariable String skuId, ModelMap modelMap, HttpServletRequest request) {


        String remoteAddr = request.getRemoteAddr();

        //sku对象
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId,remoteAddr);
        modelMap.put("skuInfo", pmsSkuInfo);
        //sku属性列表spuSaleAttrListCheckBySku
        List<PmsProductSaleAttr> attrList = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);
        modelMap.put("spuSaleAttrListCheckBySku", attrList);
        //C查詢當前sku的兄弟sku
        List<PmsSkuInfo> pmsSkuInfoList = skuService.SkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
        Map<String,String> skuSaleAttrMap = new HashMap<>();
        for (PmsSkuInfo skuInfo : pmsSkuInfoList) {
            String k ="";
            String v = skuInfo.getId();
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
                k += pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";

            }
            skuSaleAttrMap.put(k,v);

        }
        String jsonString = JSON.toJSONString(skuSaleAttrMap);
        modelMap.put("skuSaleAttrHashJsonStr", jsonString);
        return "item";
    }
}
