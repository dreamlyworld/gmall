package com.leaves.gmall.manage.dao;

import com.leaves.gmall.model.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/25 17:17
 * @Version 1.0
 */
public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {
    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("productId") String productId, @Param("skuId")String skuId);
}
