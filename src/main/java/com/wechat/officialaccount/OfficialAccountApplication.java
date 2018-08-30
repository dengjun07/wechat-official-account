package com.wechat.officialaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OfficialAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficialAccountApplication.class, args);
        System.out.println(
        "Spring Boot启动RUA!   ε=ε=ε=(~￣▽￣)~\n" +
                ".____                    .___.__   \n" +
                "|    |    ____ ___.__. __| _/|  |  \n" +
                "|    |  _/ ___<   |  |/ __ | |  |  \n" +
                "|    |__\\  \\___\\___  / /_/ | |  |__\n" +
                "|_______ \\___  > ____\\____ | |____/\n" +
                "        \\/   \\/\\/         \\/       ");
    }
}
