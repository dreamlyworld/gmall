package com.leaves.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.leaves.gmall")
public class GmallRedissonApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallRedissonApplication.class, args);
    }

}
