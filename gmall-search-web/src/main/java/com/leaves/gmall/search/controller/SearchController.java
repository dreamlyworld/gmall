package com.leaves.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.model.*;
import com.leaves.gmall.service.AttrService;
import com.leaves.gmall.service.SearchService;
import com.leaves.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @Author Chenweiwei
 * @Date 2021/2/26 16:27
 * @Version 1.0
 */

@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {

        return "index";
    }

    /*
     *@desc :
     * @Author:cheweiwei
     * @param  modelMap
     * @param  searchParam
     * @Date : 2021/3/1  16:41
     */
    @RequestMapping("list.html")
    public String list(ModelMap modelMap, SearchParam searchParam) {

        List<SearchSkuInfo> searchSkuInfoList = searchService.list(searchParam);
        modelMap.addAttribute("skuLsInfoList", searchSkuInfoList);
        //属性值名称
        Map<String,String> valueNameMap = new HashMap<>();
        if (null != searchSkuInfoList) {
            Set<String> set = new HashSet<>();
            for (SearchSkuInfo searchSkuInfo : searchSkuInfoList) {
                List<PmsSkuAttrValue> skuAttrValueList = searchSkuInfo.getSkuAttrValueList();
                for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                    String valueId = pmsSkuAttrValue.getValueId();
                    set.add(valueId);
                }
            }

            //根据valueId将属性列表值查询出来
            List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getListByValueIds(set);

            //去除当前条件中的valueid所在的属性组
            List<String> delValueIds = searchParam.getValueId();
            if (null != delValueIds){
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                while (iterator.hasNext()){
                    PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                    for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                        String valueId = pmsBaseAttrValue.getId();
                        for (String delValueId : delValueIds) {
                            if (delValueId.equals(valueId)){
                                String crumName = pmsBaseAttrInfo.getAttrName()+":"+ pmsBaseAttrValue.getValueName();
                                valueNameMap.put(delValueId,crumName);
                                //删除valueId所在的属性组
                                iterator.remove();
                            }
                        }
                    }
                }


            }
            modelMap.addAttribute("attrList", pmsBaseAttrInfos);
        }
        String urlParam = getUrlParam(searchParam);
        modelMap.addAttribute("urlParam", urlParam);
        //面包屑,就是在原来的请求中减去点击的valueId参数
        List<String> delValueIds = searchParam.getValueId();

        String keyword = searchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)){
            modelMap.addAttribute("keyword", keyword);
        }
        List<SearchCrumb> searchCrumbs = new ArrayList<>();
        if (null != delValueIds ){
            for (String delValueId : delValueIds) {
                SearchCrumb searchCrumb = new SearchCrumb();
                searchCrumb.setValueId(delValueId);
                searchCrumb.setValueName(valueNameMap.get(delValueId));
                searchCrumb.setUrlParam(getUrlParamForCrumb(searchParam,delValueId));
                searchCrumbs.add(searchCrumb);
            }
        }
        modelMap.addAttribute("attrValueSelectedList", searchCrumbs);
        return "list";
    }

    private String getUrlParamForCrumb(SearchParam searchParam,String delValueId) {
        //参数
        List<String> skuAttrValueIdList = searchParam.getValueId();
        String catalog3Id = searchParam.getCatalog3Id();
        String keyword = searchParam.getKeyword();

        String urlParam = "";
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&keyword=" + keyword;
            } else {
                urlParam = urlParam + "keyword=" + keyword;
            }
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&catalog3Id=" + catalog3Id;
            } else {
                urlParam = urlParam + "catalog3Id=" + catalog3Id;
            }
        }
        if (null != skuAttrValueIdList) {
                for (String valueId : skuAttrValueIdList) {
                    if (!valueId.equals(delValueId)){
                        urlParam = urlParam + "&valueId=" + valueId;
                    }
                }


        }
        return urlParam;
    }

    private String getUrlParam(SearchParam searchParam) {
        //参数
        List<String> skuAttrValueIdList = searchParam.getValueId();
        String catalog3Id = searchParam.getCatalog3Id();
        String keyword = searchParam.getKeyword();

        String urlParam = "";
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&keyword=" + keyword;
            } else {
                urlParam = urlParam + "keyword=" + keyword;
            }
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&catalog3Id=" + catalog3Id;
            } else {
                urlParam = urlParam + "catalog3Id=" + catalog3Id;
            }
        }
        if (null != skuAttrValueIdList) {

                for (String valueId : skuAttrValueIdList) {
                    urlParam = urlParam + "&valueId=" + valueId;
                }
        }
        return urlParam;
    }
}
