package com.app1.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class HomeController {

    public static final String CIRCUIT_BREAKER = "breaker";
    RestTemplate restTemplate = new RestTemplate();

    @Value("${app2.hostname}")
    private String app2Hostname;

    @GetMapping("/hello")
    public String hello(){
        return "this is message from app1";
    }


    @GetMapping("/call-app2")
    @CircuitBreaker(name = CIRCUIT_BREAKER,fallbackMethod= "FallBackMessage")
    public ResponseEntity<String> callApp2() {

        String fooResourceUrl
                = app2Hostname + "/hello";

           System.out.println("fooResourceUrl : "+fooResourceUrl);

        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        //Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


        return response;
    }
    public ResponseEntity<String> FallBackMessage(Exception e){


        return ResponseEntity.ok("App2 is down");
    }



}
