package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.baizhi.dao")
@SpringBootApplication
public class ZmfzApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZmfzApplication.class, args);
        System.out.println("你好");
        System.out.println("how are you");
    }

}
