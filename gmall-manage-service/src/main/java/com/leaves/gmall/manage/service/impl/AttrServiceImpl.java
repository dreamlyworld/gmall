package com.leaves.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.leaves.gmall.manage.dao.PmsBaseAttrInfoMapper;
import com.leaves.gmall.manage.dao.PmsBaseAttrValueMapper;
import com.leaves.gmall.manage.dao.PmsBaseSaleAttrMapper;
import com.leaves.gmall.model.PmsBaseAttrInfo;
import com.leaves.gmall.model.PmsBaseAttrValue;
import com.leaves.gmall.model.PmsBaseSaleAttr;
import com.leaves.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 11:41
 * @Version 1.0
 */

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo pmsBaseAttrInfo1 : pmsBaseAttrInfos) {
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo1.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            pmsBaseAttrInfo1.setAttrValueList(pmsBaseAttrValues);

        }
        return pmsBaseAttrInfos;
    }

    @Override
    public Boolean saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        if (attrValueList.size() > 0) {
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }
        return true;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }


    @Override
    public Boolean updateAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        // id不空，修改

        // 属性修改
        Example example = new Example(PmsBaseAttrInfo.class);
        example.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
        pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo, example);


        // 属性值修改
        // 按照属性id删除所有属性值
        PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
        pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
        pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDel);

        // 删除后，将新的属性值插入
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
            pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
        }


        return true;
    }

    @Override
    public List<PmsBaseSaleAttr> getaseSaleAttrListB() {
        return this.pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public List<PmsBaseAttrInfo> getListByValueIds(Set<String> set) {
        String valueIdStr = StringUtils.join(set, ",");
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selecttListByValueIds(valueIdStr);
        return pmsBaseAttrInfos;
    }
}
