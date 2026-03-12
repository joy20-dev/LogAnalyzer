package com.example.LogAnalyzer.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.LogAnalyzer.Models.LogSummary;
import com.example.LogAnalyzer.Service.LogParserService;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class TestController {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    LogParserService logParserService;

    @GetMapping("/test")
    public String test() {
        return "Key starts with: " + apiKey.substring(0, 8);
    }

    @GetMapping("/apiCall")
    public String test2(){
        return chatClient.prompt()
                    .user("hey, can you read this,type joy the boy if you can")
                    .call()
                    .content();

    }

    @PostMapping("/test-parser")
    public LogSummary testParser(@RequestBody String rawLogs){
        return logParserService.parse(rawLogs);
        

    }

}
