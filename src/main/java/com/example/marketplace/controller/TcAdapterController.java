package com.example.marketplace.controller;

import com.example.marketplace.service.TcAdapterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCheckpointRestore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@RestController
@SecurityRequirement(name = "swagger_keycloak")
public class TcAdapterController {
    private final WebClient webClient;
    private final TcAdapterService tcAdapterService;


    public TcAdapterController(WebClient webClient,TcAdapterService tcAdapterService) {
        this.webClient = webClient;
        this.tcAdapterService = tcAdapterService;
    }

    @Value("${addressservice.base.url}" )
    String  baseUrl;

    @GetMapping("/hello")
    public String welcome(){

    return "Welcome to the marketplace adapter!";
}

    @PostMapping("/kmp")
    public String connectingMP(@RequestParam String name ,
                               @RequestParam boolean closed ) throws IOException {

        return tcAdapterService.greetings(name,closed);
    }

}
