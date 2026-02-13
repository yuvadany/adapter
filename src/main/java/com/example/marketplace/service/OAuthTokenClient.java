package com.example.marketplace.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OAuthTokenClient {
	
    @Value("${apihub.client.auth.clientName}")
    private String clientId;

    @Value("${apihub.client.auth.clientSecret}")
    private String clientSecret;

    @Value("${apihub.client.auth.accessTokenUrlRL}")
    private String accessTokenUrl;
    
    public String getAccessToken() {

        RestTemplate restTemplate = new RestTemplate();


        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        // Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map<String,Object>> response =
                restTemplate.postForEntity(
                		accessTokenUrl,
                        request,
                        (Class<Map<String,Object>>) (Class<?>) Map.class
                );

        String tokenValue = (String)response.getBody().get("access_token");
        return tokenValue;
    }
    
}
