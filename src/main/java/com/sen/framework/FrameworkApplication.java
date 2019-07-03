package com.sen.framework;

import com.sen.framework.common.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Evan Huang
 * @date 2018-12-20
 */
@SpringBootApplication
public class FrameworkApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(FrameworkApplication.class, args);
//    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        new SpringApplicationBuilder(FrameworkApplication.class)
                .listeners(event -> {
                    String name = event.getClass().getName();
                    System.err.println("监听到事件：" + name);
                    stringBuilder.append(name).append("\n");
                })
                .run(args);
        FileUtils.write("D:/eventName.txt", stringBuilder.toString());
    }


    @Value("${server.port}")
    private Integer port;

    @GetMapping("/config")
    public String config() {
        return "port:" + port;
    }
}
