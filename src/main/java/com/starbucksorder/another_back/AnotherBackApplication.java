package com.starbucksorder.another_back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@MapperScan("com.starbucksorder.another_back.repository")
public class AnotherBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotherBackApplication.class, args);
	}

}
