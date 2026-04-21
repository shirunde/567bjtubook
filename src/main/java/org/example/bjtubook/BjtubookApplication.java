package org.example.bjtubook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.bjtubook.mapper")
public class BjtubookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BjtubookApplication.class, args);
    }

}
