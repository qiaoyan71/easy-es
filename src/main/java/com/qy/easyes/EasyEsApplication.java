package com.qy.easyes;

import cn.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EsMapperScan("com.qy.easyes.mapper")
public class EasyEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyEsApplication.class, args);
    }

}
