package com.pol.calendarinterceptor.calendarinterceptor.interceptors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/hello")
    public ResponseEntity<?> foo() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello, World!");
        data.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(data);
    }

}
