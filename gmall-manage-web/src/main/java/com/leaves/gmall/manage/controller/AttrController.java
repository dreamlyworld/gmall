package com.leaves.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.PmsBaseAttrInfo;
import com.leaves.gmall.model.PmsBaseAttrValue;
import com.leaves.gmall.model.PmsBaseCatalog1;
import com.leaves.gmall.model.PmsBaseSaleAttr;
import com.leaves.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 11:33
 * @Version 1.0
 */

@Controller
@CrossOrigin //允许跨域
public class AttrController {


    @Reference
    AttrService attrService;

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> spuList(String catalog3Id) {

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);

        return pmsBaseAttrInfos;
    }


    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        if (StringUtils.isBlank(pmsBaseAttrInfo.getId())) {
            attrService.saveAttrInfo(pmsBaseAttrInfo);
        } else {
            attrService.updateAttrInfo(pmsBaseAttrInfo);
        }


        return "success";
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {

        List<PmsBaseAttrValue> pmsBaseAttrInfos = attrService.getAttrValueList(attrId);

        return pmsBaseAttrInfos;
    }


    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList() {

        List<PmsBaseSaleAttr> pmsBaseAttrInfos = attrService.getaseSaleAttrListB();

        return pmsBaseAttrInfos;
    }


}
