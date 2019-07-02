package com.sen.framework;

import com.baomidou.mybatisplus.core.conditions.Condition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Evan Huang
 * @date 2018-12-20
 */
@SpringBootApplication
public class FrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameworkApplication.class, args);
    }

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/config")
    public String config() {
        return "port:" + port;
    }
}
