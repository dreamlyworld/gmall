package com.leaves.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.PmsProductInfo;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 15:41
 * @Version 1.0
 */

@Controller
@CrossOrigin //允许跨域
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        if (StringUtils.isBlank(pmsSkuInfo.getSkuDefaultImg()))
            pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());

        skuService.saveSkuInfo(pmsSkuInfo);

        return "success";
    }


}
