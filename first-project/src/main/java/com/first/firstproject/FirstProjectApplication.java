package com.first.firstproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@ComponentScan({ "com.first.*" })
@PropertySource({ "classpath:application.properties" })
public class FirstProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder webBuilder, @Value("${baseurl}") String baseUrl) {

		ClientHttpConnector httpConnector = new ReactorClientHttpConnector(HttpClient.create().compress(true));

		// ClientHttpConnector httpConnector = new
		// ReactorClientHttpConnector(HttpClient.newConnection());

		return webBuilder.clientConnector(httpConnector).baseUrl(baseUrl).build();

	}

}
