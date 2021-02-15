package com.leaves.gmall.manage.dao;

import com.leaves.gmall.model.PmsProductSaleAttr;
import com.leaves.gmall.model.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 15:51
 * @Version 1.0
 */
public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(@Param("productId") String productId);
}
