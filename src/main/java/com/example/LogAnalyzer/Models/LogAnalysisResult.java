package com.example.LogAnalyzer.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogAnalysisResult {
    private LogSummary summary;
    private String aiAnalysis;
}