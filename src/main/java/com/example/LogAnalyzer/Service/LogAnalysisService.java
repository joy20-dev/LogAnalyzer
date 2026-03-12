package com.example.LogAnalyzer.Service;

import com.example.LogAnalyzer.Models.LogSummary;
import com.example.LogAnalyzer.Models.LogAnalysisResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogAnalysisService {

    private final ChatClient chatClient;
    private final LogParserService logParserService;

    public LogAnalysisResult analyze(String rawLogs) {
        // Step 1 — parse logs in Java first
        LogSummary summary = logParserService.parse(rawLogs);

        // Step 2 — build structured prompt
        String prompt = buildPrompt(summary);

        // Step 3 — send to LLM
        String aiAnalysis = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        // Step 4 — return both summary and AI analysis
        return LogAnalysisResult.builder()
                .summary(summary)
                .aiAnalysis(aiAnalysis)
                .build();
    }

    private String buildPrompt(LogSummary summary) {
        return String.format("""
                Analyze the following log summary and provide insights:
                
                STATISTICS:
                - Total log lines: %d
                - Errors: %d
                - Warnings: %d
                - Info: %d
                
                EXCEPTION FREQUENCY:
                %s
                
                SAMPLE ERROR LINES:
                %s
                
                Please provide:
                1. Plain English summary of what is happening
                2. Most critical issues to investigate first
                3. Likely root causes for top exceptions
                4. Specific suggested fixes
                5. What to monitor going forward
                """,
                summary.getTotalLines(),
                summary.getErrorCount(),
                summary.getWarnCount(),
                summary.getInfoCount(),
                formatMap(summary.getExceptionFrequency()),
                String.join("\n", summary.getSampleErrorLines())
        );
    }

    private String formatMap(java.util.Map<String, Integer> map) {
        if (map.isEmpty()) return "No known exceptions detected";
        return map.entrySet()
                  .stream()
                  .map(e -> "- " + e.getKey() + ": " + e.getValue() + " times")
                  .collect(java.util.stream.Collectors.joining("\n"));
    }
}
