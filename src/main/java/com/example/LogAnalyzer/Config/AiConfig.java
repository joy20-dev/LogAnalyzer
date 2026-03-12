package com.example.LogAnalyzer.Config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class AiConfig {
    @Bean       //"register this as a Spring bean"
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                    .defaultSystem("""
                    You are an expert software engineer 
                    specializing in application log analysis.
                    Always be specific, technical, and actionable
                    in your responses.
                    """)
                    .build();
    }

}
