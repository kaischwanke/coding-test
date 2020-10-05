package au.com.ks.codingtest.demo.service;

import au.com.ks.codingtest.demo.model.LogLine;
import au.com.ks.codingtest.demo.parser.LogLineParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyticsServiceIntegrationTest {

    private AnalyticsService analyticsService = new AnalyticsService();

    private static LogLineParser parser;

    private static List<LogLine> lines;

    @BeforeAll
    static void setup() throws IOException {
        parser = new LogLineParser();
        lines = new ArrayList<>();

        Path path = Paths.get("src/main/resources/programming-task-example-data.log");

        List<String> allLogLines = Files.readAllLines(path);
        for (String logLine : allLogLines) {
            lines.add(parser.parseEntry(logLine));
        }
    }

    @Test
    void shouldFindUniqueIPs() {

        Long uniqueIPsCount = analyticsService.numberOfUniqueIPs(lines);
        assertEquals(11, uniqueIPsCount);
    }

    @Test
    void shouldShowMostVisitedURLs() {
        List<String> mostVisitedURLs = analyticsService.mostVisitedURLs(lines);
        assertEquals(3, mostVisitedURLs.size());
        assertEquals("/docs/manage-websites/", mostVisitedURLs.get(0));
        assertEquals("/asset.css", mostVisitedURLs.get(1));
        assertEquals("/download/counter/", mostVisitedURLs.get(2));
    }

    @Test
    void shouldShowMostActiveIPs() {
        List<String> mostActiveIPs = analyticsService.mostActiveIPs(lines);
        assertEquals(3, mostActiveIPs.size());
        assertEquals("168.41.191.40", mostActiveIPs.get(0));
        assertEquals("72.44.32.10", mostActiveIPs.get(1));
        assertEquals("50.112.00.11", mostActiveIPs.get(2));

    }

}
