package com.leaves.gmall.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.leaves.gmall.user.dao")
public class GmallUserApplication {
    private final static Logger logger = LoggerFactory.getLogger(GmallUserApplication.class);

    public static void main(String[] args) {
        logger.info("项目开始启动。。。。。。");
        SpringApplication.run(GmallUserApplication.class, args);
        logger.info("项目启动成功。。。。。。");
        logger.info("---------------------welcome---------------");
    }

}
