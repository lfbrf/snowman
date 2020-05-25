package br.com.snowman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
    
}
