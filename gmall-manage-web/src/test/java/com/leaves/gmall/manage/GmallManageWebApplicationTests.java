package com.leaves.gmall.manage;


import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {

        String file = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();

        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="D:/studyFile/谷里商场/谷里商场/day01-2019年5月22日/01-视频/01 谷粒商城的简介.avi";
        String[] upload_file = storageClient.upload_file(orginalFilename, "avi", null);

        String url = "http://192.168.50.101";

        for (int i = 0; i < upload_file.length; i++) {
            url += "/"+upload_file[i];
        }
        System.out.println("url = " + url);
    }

}
