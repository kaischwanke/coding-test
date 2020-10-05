package au.com.ks.codingtest.demo.parser;

import au.com.ks.codingtest.demo.model.LogLine;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility to parse log files
 * Note: Most likely there are open source libraries available for this.
 */
public class LogLineParser {

    // possible improvement: make regex more robust, test against large data sets
    private static final String LOG_ENTRY_PATTERN =
            "^(\\S+) " + // ip
                    "(\\S+) " + // identity
                    "(\\S+) " + // user
                    "(\\[.*\\]) (\".*\") " + //date
                    "(\\S+) " + // request
                    "(\\S+) " + //status
                    "(\".*\") " + //size
                    "(\".*\")" +  // user agent
                    "(.*)?"; // additional info


    public LogLine parseEntry(String line) {

        final Pattern pattern = Pattern.compile(LOG_ENTRY_PATTERN);
        final Matcher matcher = pattern.matcher(line);

        // todo: introduce proper logging
        if (!matcher.matches()) {
            System.out.println("Error parsing line:");
            System.out.println(line);
            return null;
        }

        return LogLine.builder()
                .ip(matcher.group(1))
                .identity(matcher.group(2))
                .user(matcher.group(3))
                .dateAsString(matcher.group(4))
                .request(matcher.group(5).replaceAll("\"", ""))
                .statusCode(Integer.parseInt(matcher.group(6)))
                .size(Integer.parseInt(matcher.group(7)))
                .auth(StringUtils.replace(matcher.group(8), "\"", ""))
                .userAgent(StringUtils.replace(matcher.group(9), "\"", ""))
                .additionalInfo(matcher.group(10))
                .build();

    }

}
