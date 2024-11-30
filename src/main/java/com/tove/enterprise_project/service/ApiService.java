package com.tove.enterprise_project.service;

import com.tove.enterprise_project.model.dto.ApiGameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    private final WebClient webClient;

    @Autowired
    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<List<ApiGameDTO>> fetchGamesByTitle(String title, String maxDate, String minDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/search/{title}")
                        .queryParamIfPresent("maxDate", Optional.ofNullable(maxDate))
                        .queryParamIfPresent("minDate", Optional.ofNullable(minDate))
                        .build(title))
                .retrieve()
                .bodyToFlux(ApiGameDTO.class)
                .collectList();
    }



}
