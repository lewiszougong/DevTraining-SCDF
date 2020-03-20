package com.springclouddataflow.courierservice;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.List;

@SpringBootApplication
@EnableBinding(Sink.class)
public class CourierServiceApplication {
	Logger logger= LoggerFactory.getLogger(CourierServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CourierServiceApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void orderDispatched(List<Product> products){
		products.forEach(product -> {
					logger.info("Order delivered to your mailing address:" + product);
				});

	}

}
