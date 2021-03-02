package com.leaves.gmall.service;

import com.leaves.gmall.model.PmsSkuInfo;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 15:48
 * @Version 1.0
 */
public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsProductInfo);


    PmsSkuInfo getSkuById(String skuId,String remoteAddr);
    PmsSkuInfo getSkuByIdFromDB(String skuId);



    List<PmsSkuInfo> SkuSaleAttrValueListBySpu(String productId);


    List<PmsSkuInfo> getAllSkuInfo();
}
