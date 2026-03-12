package com.example.LogAnalyzer.Controller;

import com.example.LogAnalyzer.Models.LogAnalysisResult;
import com.example.LogAnalyzer.Service.LogAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogAnalysisService logAnalysisService;

    @PostMapping("/analyze")
    public LogAnalysisResult analyze(@RequestBody String rawLogs) {
        return logAnalysisService.analyze(rawLogs);
    }
}
