package com.leaves.gmall.manage.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Chenweiwei
 * @Date 2021/1/27 10:36
 * @Version 1.0
 */
public class UploadUtil {


    public static String uploeadImage(MultipartFile multipartFile) {
        String imageUrl = "http://192.168.50.101";
        //加载配置文件
        String filePath = UploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            //初始化
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;

        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StorageClient storageClient = new StorageClient(trackerServer, null);
        try {
            //获取图片格式
            String originalFilename = multipartFile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String imageFormatName = originalFilename.substring(index + 1);
            //获得上传图片的二进制对象
            byte[] bytes = multipartFile.getBytes();
            String[] upload_file = storageClient.upload_file(bytes, imageFormatName, null);
            for (int i = 0; i < upload_file.length; i++) {
                imageUrl += "/" + upload_file[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageUrl;
    }


}
