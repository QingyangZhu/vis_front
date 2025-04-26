package com.vis.accountingvis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.vis.accountingvis.mapper")
public class AccountingVisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingVisApplication.class, args);
    }

}
