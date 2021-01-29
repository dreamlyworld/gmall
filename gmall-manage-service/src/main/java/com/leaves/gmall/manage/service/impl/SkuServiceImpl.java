package com.leaves.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.leaves.gmall.manage.dao.PmsSkuAttrValueMapper;
import com.leaves.gmall.manage.dao.PmsSkuImageMapper;
import com.leaves.gmall.manage.dao.PmsSkuInfoMapper;
import com.leaves.gmall.manage.dao.PmsSkuSaleAttrValueMapper;
import com.leaves.gmall.model.PmsSkuAttrValue;
import com.leaves.gmall.model.PmsSkuImage;
import com.leaves.gmall.model.PmsSkuInfo;
import com.leaves.gmall.model.PmsSkuSaleAttrValue;
import com.leaves.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Transient;
import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 15:50
 * @Version 1.0
 */

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsProductInfo) {
        pmsSkuInfoMapper.insert(pmsProductInfo);
        String skuId = pmsProductInfo.getId();
        //插入图片关联
        List<PmsSkuImage> skuImageList = pmsProductInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        //插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsProductInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }


        //插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsProductInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo1.setSkuImageList(imageList);
        return pmsSkuInfo1;
    }

}
