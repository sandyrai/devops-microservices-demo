package com.example.app2.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class App2Controller {
    public static final String CIRCUIT_BREAKER = "breaker";
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello")
    public String hello(){
        return "this is message from app2";
    }


    @GetMapping("/call-app1")
    @CircuitBreaker(name = CIRCUIT_BREAKER,fallbackMethod= "FallBackMessage")
    public ResponseEntity<String> callApp2() {

        String fooResourceUrl
                = "http://localhost:8081/hello";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        //Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


        return response;
    }
    public ResponseEntity<String> FallBackMessage(Exception e){


        return ResponseEntity.ok("App 1 is down");
    }

    }

