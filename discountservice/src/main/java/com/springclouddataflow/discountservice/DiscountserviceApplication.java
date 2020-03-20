package com.springclouddataflow.discountservice;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableBinding(Processor.class)
public class DiscountserviceApplication {
	Logger logger= LoggerFactory.getLogger(DiscountserviceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DiscountserviceApplication.class, args);
	}

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public List<Product> addDiscountToProduct(List<Product> products){
		List<Product> productList=new ArrayList<>();
		for (Product product:products){
			logger.info("Product actual price is {}", product.getPrice());
			if (product.getPrice()>3000){
				productList.add(calculatePrice(product,10));
			}
			else if (product.getPrice()>100){
				productList.add(calculatePrice(product,5));
			}

		}
		return products;
	}

	public Product calculatePrice(Product product, int percentage){
		double actualPrice=product.getPrice();
		double discountPrice=actualPrice*(100-percentage)/100;
		product.setPrice(discountPrice);
		logger.info("Product actual price is {}, after discount total price is {} ", actualPrice, product.getPrice());
		return product;
	}
}
