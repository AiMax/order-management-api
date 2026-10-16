package com.aimax.order_management_api.rest_service_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") //Ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    public Greeting greeting(@RequestParam(defaultValue = "World") String name){
        return new Greeting(counter.getAndIncrement(), template.formatted(name)); // Thanks to Spring’s HTTP message converter support, you need not do this conversion manually.
    }


}
