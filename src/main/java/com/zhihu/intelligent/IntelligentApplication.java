package com.zhihu.intelligent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IntelligentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentApplication.class, args);
    }

}
