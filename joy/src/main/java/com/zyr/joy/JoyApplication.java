package com.zyr.joy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhengyongrui
 */
@SpringBootApplication
@ComponentScan("com.zyr")
public class JoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoyApplication.class, args);
	}

}
