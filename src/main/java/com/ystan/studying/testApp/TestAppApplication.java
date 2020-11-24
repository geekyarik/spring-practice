package com.ystan.studying.testApp;

import com.ystan.studying.testApp.beans.RandomHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TestAppApplication.class, args);

		RandomHolder bean = (RandomHolder) context.getBean(RandomHolder.class);
		//System.out.println(bean.getValue());
	}
}
