package com.example.ai_social_assistant.controller.UserController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TestController {
    @GetMapping("/data")
    public ResponseEntity<Map<String, String>> getData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, this is a JSON response!");
        return ResponseEntity.ok(response);
    }
}
