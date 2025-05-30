package com.bes.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @RequestMapping("/hello/{name}")
    public ResponseEntity<String> getHelloName(@PathVariable String name) {
        return ResponseEntity.ok("Hello " + name);
    }
}