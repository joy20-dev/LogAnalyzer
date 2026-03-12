package com.example.LogAnalyzer.Models;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data       // generates getters, setters, toString
@Builder    // generates builder pattern
public class LogSummary {
    private int totalLines;
    private int errorCount;
    private int warnCount;
    private int infoCount;
    private Map<String, Integer> exceptionFrequency;
    private List<String> sampleErrorLines;
}
