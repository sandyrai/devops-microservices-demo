package com.app1.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.function.Supplier;
import java.util.Arrays;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import java.util.concurrent.TimeoutException;
import org.springframework.web.client.RestClientException;


@RestController
public class HomeController {

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
    public String callApp2() {
        // Define the URL of App2's endpoint
        String app2Url = "http://localhost:8082/hello";

        // Create a Supplier that makes a call to App2 using RestTemplate
        Supplier<String> supplier = () -> restTemplate.getForObject(app2Url, String.class);

        // Decorate the Supplier with a Circuit Breaker and a Fallback function
        // The fallback function will be called when any of the specified exceptions are thrown
        Supplier<String> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withFallback(Arrays.asList(TimeoutException.class,
                                CallNotPermittedException.class,
                                RestClientException.class),
                        // Fallback function to handle specified exceptions
                        throwable -> app2Fallback())
                .decorate();

        // Execute the decorated Supplier and store the result in a Try instance
        Try<String> result = Try.ofSupplier(decoratedSupplier);

        // If the Try instance contains a successful result, return it
        // Otherwise, return the result of the fallback function
        return result.getOrElseGet(throwable -> app2Fallback());
    }

    // Fallback function to be called when the Circuit Breaker is open or a specified exception occurs
    private String app2Fallback() {
        return "App2 is currently unavailable. Please try again later.";
    }


}
