package org.yoon.msamemberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MsaMemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaMemberServiceApplication.class, args);
    }

}
