package au.com.ks.codingtest.demo.service;

import au.com.ks.codingtest.demo.model.LogLine;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {

    private AnalyticsService analyticsService = new AnalyticsService();

    @Test
    void shouldGetNumberOfUniqueIPs() {
        ImmutableList<LogLine> ipAddresses = ImmutableList.<LogLine>builder()
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip2").build())
                .add(LogLine.builder().ip("ip1").build())
                .build();
        assertEquals(2, analyticsService.numberOfUniqueIPs(ipAddresses));
    }

    @Test
    void shouldFindMostVisitedURLs() {
        ImmutableList<LogLine> ipAddresses = ImmutableList.<LogLine>builder()
                .add(LogLine.builder().request("GET url1 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url1 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url1 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url2 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url2 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url3 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url4 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url4 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url4 HTTP/1.1").build())
                .add(LogLine.builder().request("GET url4 HTTP/1.1").build())
                .build();
        //Collections.shuffle(new ArrayList<>(ipAddresses));
        List<String> top3 = analyticsService.mostVisitedURLs(ipAddresses);
        assertEquals(3, top3.size());
        assertEquals("url4", top3.get(0));
        assertEquals("url1", top3.get(1));
        assertEquals("url2", top3.get(2));
    }

    @Test
    void shouldFindMostActiveIPs() {
        ImmutableList<LogLine> ipAddresses = ImmutableList.<LogLine>builder()
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip1").build())
                .add(LogLine.builder().ip("ip2").build())
                .add(LogLine.builder().ip("ip2").build())
                .add(LogLine.builder().ip("ip2").build())
                .add(LogLine.builder().ip("ip3").build())
                .add(LogLine.builder().ip("ip3").build())
                .add(LogLine.builder().ip("ip4").build())
                .build();
        List<String> mostActiveIPs = analyticsService.mostActiveIPs(ipAddresses);
        assertEquals(3, mostActiveIPs.size());
        assertEquals("ip1", mostActiveIPs.get(0));
        assertEquals("ip2", mostActiveIPs.get(1));
        assertEquals("ip3", mostActiveIPs.get(2));
    }

    @Test
    void shouldCleanUpRequestUrl() {
        String request = "GET /something HTTP/1.1";
        assertEquals("/something", analyticsService.cleanRequestString(request));
    }

}
