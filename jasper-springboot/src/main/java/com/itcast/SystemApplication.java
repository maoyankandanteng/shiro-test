package com.itcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.itcast")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class);
    }
}
