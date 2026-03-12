package com.example.LogAnalyzer.Service;



import com.example.LogAnalyzer.Models.LogSummary;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LogParserService {

    // common exception types to look for
    private static final List<String> KNOWN_EXCEPTIONS = List.of(
        "NullPointerException",
        "IllegalArgumentException",
        "RuntimeException",
        "SQLException",
        "IOException",
        "TimeoutException",
        "OutOfMemoryError",
        "ClassNotFoundException",
        "IndexOutOfBoundsException",
        "NumberFormatException"
    );

    public LogSummary parse(String rawLogs) {
        // split into individual lines
        String[] lines = rawLogs.split("\n");

        int errorCount = 0;
        int warnCount = 0;
        int infoCount = 0;
        Map<String, Integer> exceptionFrequency = new HashMap<>();
        List<String> sampleErrorLines = new ArrayList<>();

        for (String line : lines) {
            // classify line by log level
            if (line.contains("ERROR")) {
                errorCount++;
                // save up to 10 sample error lines for LLM
                if (sampleErrorLines.size() < 10) {
                    sampleErrorLines.add(line);
                }
                // check for known exceptions in this line
                extractExceptions(line, exceptionFrequency);

            } else if (line.contains("WARN")) {
                warnCount++;

            } else if (line.contains("INFO")) {
                infoCount++;
            }
        }

        return LogSummary.builder()
                .totalLines(lines.length)
                .errorCount(errorCount)
                .warnCount(warnCount)
                .infoCount(infoCount)
                .exceptionFrequency(exceptionFrequency)
                .sampleErrorLines(sampleErrorLines)
                .build();
    }

    private void extractExceptions(String line,
                                    Map<String, Integer> freq) {
        for (String exception : KNOWN_EXCEPTIONS) {
            if (line.contains(exception)) {
                // if key exists add 1, if not start at 1
                freq.put(exception, freq.getOrDefault(exception, 0) + 1);
            }
        }
    }
}
