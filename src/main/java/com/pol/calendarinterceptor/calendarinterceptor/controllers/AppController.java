package com.pol.calendarinterceptor.calendarinterceptor.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AppController {

    @GetMapping("/")
    public ResponseEntity<?> foo(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello, World!");
        data.put("timestamp", System.currentTimeMillis());
        data.put("message", request.getAttribute("message"));
        return ResponseEntity.ok(data);
    }

}
