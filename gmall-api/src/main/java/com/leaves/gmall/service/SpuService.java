package com.leaves.gmall.service;

import com.leaves.gmall.model.PmsProductInfo;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/25 16:08
 * @Version 1.0
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);
}
