package cn.css0209.mall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("服务启动...");
        SpringApplication.run(Application.class, args);
    }

}
