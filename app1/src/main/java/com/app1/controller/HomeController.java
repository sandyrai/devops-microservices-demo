package com.app1.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class HomeController {

    public static final String CIRCUIT_BREAKER = "breaker";
    private RestTemplate restTemplate;

    private CircuitBreaker circuitBreaker;


    @Autowired
    public HomeController(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate = new RestTemplate();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("app2CircuitBreaker");
    }

    @GetMapping("/hello")
    public String hello() {
        return "this is message from app1";
    }


    @GetMapping("/call-app2")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = CIRCUIT_BREAKER,fallbackMethod= "FallBackMessage")
    public String callApp2() {

        String fooResourceUrl
                = "http://localhost:8082/hello";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        //Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        return "app1 called";
    }
    public String FallBackMessage(Exception e){

        return "App2 is currently unavailable. Please try again later.";
    }



}
