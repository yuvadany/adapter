package com.example.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class TcAdapterService implements ITcAdapterFetchService {


    @Value("${apihub.baseUrl}")
    private String apiBaseUrl;

    @Value("${addressservice.base.url}")
    private String apiUrl;

    @Autowired
    OAuthTokenClient oAuthTokenClient;

    @Autowired
    WebClient webClient;

    @Override
    public String greetings(String name, boolean closed) throws IOException {
        String accessToken = oAuthTokenClient.getAccessToken();

        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/greetings")
                        .queryParam("name", name)
                        .queryParam("closed", closed)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody ->
                                        Mono.error(new IOException(
                                                clientResponse.statusCode() + " " + errorBody))))
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
