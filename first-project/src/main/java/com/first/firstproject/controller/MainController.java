package com.first.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private WebClient webclient;

	@Value("${uri}")
	String uri;

	@GetMapping("/test")
	@ResponseBody
	public void checkValue() {
		try {
			webclient.post().uri(uri).exchange().onErrorResume(x -> {
				x.printStackTrace();
				return Mono.just(ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build());
			}).flatMap(t -> {
				if (!t.statusCode().is2xxSuccessful()) {
					System.out.println("Error not 200");
					return Mono.just("error");
				}
				return Mono.just("success");
			}).subscribe();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
