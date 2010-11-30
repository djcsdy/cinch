package net.noiseinstitute.cinch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest {

    public enum Method {
        GET, HEAD
    }

    private static final String PATH_PATTERN_STRING = "([^? ]*)(?:\\?[^ ]*)";

    private static final String HTTP_VERSION_PATTERN_STRING = "HTTP\\d+\\.\\d+";

    private static final String GET_REQUEST_PATTERN_STRING =
            "^GET " + PATH_PATTERN_STRING + " " + HTTP_VERSION_PATTERN_STRING + "$";

    private static final String HEAD_REQUEST_PATTERN_STRING =
            "^HEAD " + PATH_PATTERN_STRING + " " + HTTP_VERSION_PATTERN_STRING + "$";

    private static final Pattern[] REQUEST_LINE_PATTERNS;

    private static final Method[] METHODS;

    static {
        REQUEST_LINE_PATTERNS = new Pattern[]{
                Pattern.compile(GET_REQUEST_PATTERN_STRING),
                Pattern.compile(HEAD_REQUEST_PATTERN_STRING)
        };
        METHODS = new Method[]{
                Method.GET,
                Method.HEAD
        };
    }

    private Method method;

    private HTTPRequest () {
    }

    public Method getMethod () {
        return method;
    }

    public static HTTPRequest parseRequest (InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "US-ASCII"));

        // For robustness skip blank lines, per RFC2616.
        String line;
        do {
            line = reader.readLine();
        } while ("".equals(line));

        if (line == null) {
            return null;
        }

        HTTPRequest request = new HTTPRequest();

        request.method = parseRequestLine(line);
        if (request.method == null) {
            return null;
        }

        return request;
    }

    private static Method parseRequestLine (String line) {
        int methodIndex;
        for (methodIndex = 0; methodIndex < REQUEST_LINE_PATTERNS.length; ++methodIndex) {
            Pattern pattern = REQUEST_LINE_PATTERNS[methodIndex];
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                break;
            }
        }

        if (methodIndex == METHODS.length) {
            return null;
        } else {
            return METHODS[methodIndex];
        }
    }

}
