package com.leaves.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leaves.gmall.manage.util.UploadUtil;
import com.leaves.gmall.model.PmsBaseAttrInfo;
import com.leaves.gmall.model.PmsProductInfo;
import com.leaves.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/25 16:01
 * @Version 1.0
 */

@Controller
@CrossOrigin //允许跨域
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){

        List<PmsProductInfo> pmsBaseAttrInfos = spuService.spuList(catalog3Id);

        return pmsBaseAttrInfos;
    }
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){

       spuService.saveSpuInfo(pmsProductInfo);

        return "success";
    }


    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){

        System.out.println("开始上传");
        String imageUlr = UploadUtil.uploeadImage(multipartFile);


        System.out.println(imageUlr);

        return imageUlr;
    }

}
