package au.com.ks.codingtest.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogLine {

    String ip;
    String identity;
    String user;
    String dateAsString;
    String request; // could be separated into method (GET, POST, ...), url and type (HTTP/1.1)
    int statusCode;
    int size;
    String auth;
    String userAgent;
    String additionalInfo;

}
