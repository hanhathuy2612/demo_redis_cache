package com.example.redis_java_spring_boot_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RedisJavaSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisJavaSpringBootDemoApplication.class, args);
    }

}
