package cn.luijp.escserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.luijp.escserver.mapper")
public class EscServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscServerApplication.class, args);
    }

}
