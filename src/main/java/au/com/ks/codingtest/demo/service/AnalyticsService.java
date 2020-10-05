package au.com.ks.codingtest.demo.service;

import au.com.ks.codingtest.demo.model.LogLine;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

// For simplicity the arguments to all methods is a list of log file lines
// This could also be file, or path to a file
public class AnalyticsService {

    public static final int MAX_SIZE = 3;
    public static final String REQUEST_URL_CLEAN_REGEX = "^(GET|POST)|(HTTP/1.1)$";

    private final Comparator<Map.Entry<String, List<LogLine>>> bySizeComparator = Map.Entry.comparingByValue(Comparator.comparingInt(List::size));

    public Long numberOfUniqueIPs(List<LogLine> logLines) {
        return logLines.stream().map(LogLine::getIp).distinct().count();
    }

    // Note: requirement is not clear:
    // If a URL was available under various methods (GET, POST, ...), should each invocation count towards to total? Or are they considered different URLs?
    // For simplicity in this implementation the method is not part of the 'uniqueness' of the url,
    // Also, I have implemented 'top 3' as a list with the size of 3, if multiple occurrences qualify for place 3, only one of them will be returned
    // possible improvement: group URLs together if they have the same hit count
    public List<String> mostVisitedURLs(List<LogLine> logLines) {
        logLines.forEach(logLine -> logLine.setRequest(cleanRequestString(logLine.getRequest())));
        return extractTopMatches(logLines, LogLine::getRequest);
    }

    // Note: Same as above: There could be multiple IPs with the same hit count
    // but we just return the top 3 (even if the one in 4th place had the same hit count as the 3rd)
    public List<String> mostActiveIPs(List<LogLine> logLines) {
        return extractTopMatches(logLines, LogLine::getIp);
    }

    protected List<String> extractTopMatches(List<LogLine> logLines, Function<LogLine, String> extractionFunction) {
        Map<String, List<LogLine>> grouped = logLines.stream().collect(Collectors.groupingBy(extractionFunction, LinkedHashMap::new, toList()));
        List<Map.Entry<String, List<LogLine>>> sorted = grouped.entrySet().stream().sorted(bySizeComparator).collect(toList());
        Collections.reverse(sorted);
        List<Map.Entry<String, List<LogLine>>> topMatches = sorted.stream().limit(MAX_SIZE).collect(toList());
        return topMatches.stream().map(Map.Entry::getKey).collect(toList());
    }

    protected String cleanRequestString(String url) {
        return url.replaceAll(REQUEST_URL_CLEAN_REGEX, "").trim();
    }


}
