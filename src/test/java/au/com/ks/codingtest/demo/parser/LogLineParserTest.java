package au.com.ks.codingtest.demo.parser;

import au.com.ks.codingtest.demo.model.LogLine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogLineParserTest {

    private final LogLineParser parser = new LogLineParser();

    @Test
    void shouldParseLogLine() {
        String SPACE = " ";
        String ip = "177.71.128.21";
        String identity = "-";
        String user = "-";
        String date = "[10/Jul/2018:22:21:28 +0200]";
        String request = "GET /intranet-analytics/ HTTP/1.1";
        int status = 200;
        int size = 3574;
        String auth = "-";
        String userAgent = "Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7";
        String testLine = ip + SPACE + identity + SPACE + user + SPACE + date + SPACE + "\"" + request + "\"" +SPACE +  status + SPACE + size + SPACE + "\"" + auth + "\"" + SPACE + "\"" + userAgent + "\"";
        LogLine logLine = parser.parseEntry(testLine);
        assertEquals(ip, logLine.getIp());
        assertEquals(identity, logLine.getIdentity());
        assertEquals(user, logLine.getUser());
        assertEquals(date, logLine.getDateAsString());
        assertEquals(request, logLine.getRequest());
        assertEquals(status, logLine.getStatusCode());
        assertEquals(size, logLine.getSize());
        assertEquals(auth, logLine.getAuth());
        assertEquals(userAgent, logLine.getUserAgent());
    }

    @Test
    void shouldParseAllLinesOfTestFile() throws IOException {

        Path path = Paths.get("src/main/resources/programming-task-example-data.log");

        List<String> allLogLines = Files.readAllLines(path);
        int numberOfLines = allLogLines.size();
        int parseSuccess = 0;
        for (String string : allLogLines) {
            LogLine logLine = parser.parseEntry(string);
            if (logLine != null) {
                parseSuccess++;
            }
        }
        assertEquals(numberOfLines, parseSuccess);
    }

}
