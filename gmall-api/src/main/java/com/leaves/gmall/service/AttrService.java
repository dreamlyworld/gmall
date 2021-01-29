package com.leaves.gmall.service;

import com.leaves.gmall.model.PmsBaseAttrInfo;
import com.leaves.gmall.model.PmsBaseAttrValue;
import com.leaves.gmall.model.PmsBaseSaleAttr;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 11:40
 * @Version 1.0
 */
public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    Boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);


    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    Boolean updateAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseSaleAttr> getaseSaleAttrListB();
}
