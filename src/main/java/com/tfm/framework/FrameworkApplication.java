package com.tfm.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Evan Huang
 * @date 2018-12-20
 */
@SpringBootApplication
@MapperScan("com.tfm.framework.dao.mapper")
public class FrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameworkApplication.class, args);
    }
}
