package com.first.firstproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class TestHandler {

	@Autowired
	private WebClient webclient;

	public Mono<String> callAPI(String str) {
		return webclient.post().uri("url").bodyValue(str).exchange().onErrorResume(x -> {
			x.printStackTrace();
			return Mono.just(ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build());
		}).flatMap(t -> {
			if (!t.statusCode().is2xxSuccessful()) {
				System.out.println("Error not 200");
				return Mono.just("error");
			}
			return Mono.just("success");
		});

	}
}
