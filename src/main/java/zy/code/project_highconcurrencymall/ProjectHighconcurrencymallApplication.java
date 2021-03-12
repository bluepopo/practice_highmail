package zy.code.project_highconcurrencymall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan(value = "zy.code.project_highconcurrencymall.dao")
@SpringBootApplication
public class ProjectHighconcurrencymallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectHighconcurrencymallApplication.class, args);
    }

}
