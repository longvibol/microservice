package com.piseth.bank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	
	@Bean
	public RouteLocator myRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/vibolbank/account/**")
						.filters(f ->f.rewritePath("/vibolbank/account/(?<segment>.*","/${segment}"))
						.uri("lb://ACCOUNT")).
				route(p -> p
						.path("/vibolbank/loan/**")
						.filters(f ->f.rewritePath("/vibolbank/loan/(?<segment>.*","/${segment}"))
						.uri("lb://LOAN")).
				
				route(p -> p
						.path("/vibolbank/card/**")
						.filters(f ->f.rewritePath("/vibolbank/card/(?<segment>.*","/${segment}"))
						.uri("lb://CARD")).build();			
						
	}

}