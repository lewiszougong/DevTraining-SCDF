package com.springclouddataflow.productservice;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableBinding(Source.class)
public class ProductServiceApplication {
	Logger logger= LoggerFactory.getLogger(ProductServiceApplication.class);
	//a method that annotated with @InboundChannelAdapter cannot accept any parameter								//maxmessagesperpoll: max  number of message received in each poll

	@Bean																											//fixeddelay: every 10 second generated once
	@InboundChannelAdapter(value = Source.OUTPUT, poller=@Poller(fixedDelay= "10000", maxMessagesPerPoll = "1"))	//the 'value' bean name to send the Message.
	public MessageSource<List<Product>> addProducts(){
		List<Product> products= Stream.of(new Product(101,"Iphone 11",3399)
				, new Product(102, "Book: Once upon a time",299)
				, new Product(103, "Ferrari SF71H",4999))
				.collect(Collectors.toList());
		logger.info("product: {}", products);
		return ()-> MessageBuilder.withPayload(products).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
