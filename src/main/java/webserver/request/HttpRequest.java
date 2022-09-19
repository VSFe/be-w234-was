package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String host;
    private String directory;
    private String MIMEType;
    private HttpMethod httpMethod;
    private Map<String, String> parameters;

    public HttpRequest() {
    }

    public HttpRequest(String directory, HttpMethod httpMethod) {
        this.directory = directory;
        this.httpMethod = httpMethod;
    }

    public HttpRequest(String host, String directory, String MIMEType, HttpMethod httpMethod) {
        this.host = host;
        this.directory = directory;
        this.MIMEType = MIMEType;
        this.httpMethod = httpMethod;
    }

    public String getHost() {
        return host;
    }

    public String getDirectory() {
        return directory;
    }

    public String getMIMEType() {
        return MIMEType;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    private static String getHeaderName(String s) {
        return s.split(" ")[0].replace(":", "");
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "host='" + host + '\'' +
                ", directory='" + directory + '\'' +
                ", MIMEType='" + MIMEType + '\'' +
                ", httpMethod=" + httpMethod +
                ", parameters=" + parameters +
                '}';
    }

    public static HttpRequest requestInfoOf(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String headerStr = br.readLine();
        String path = HttpRequestUtil.getPath(headerStr);
        HttpMethod httpMethod = HttpMethod.valueOf(headerStr.split(" ")[0].toUpperCase());
        HttpRequest httpRequest = new HttpRequest(path, httpMethod);
        httpRequest.parameters = HttpRequestUtils.parseQueryString(path);

        while ((headerStr = br.readLine()) != null && !"".equals(headerStr)) {
            logger.debug(headerStr);
            String headerName = getHeaderName(headerStr);

            switch (headerName) {
                case "Host":
                    httpRequest.host = headerStr.split(" ")[1];
                    break;
                case "Accept":
                    httpRequest.MIMEType = headerStr.split(" ")[1].split(",")[0];
                    break;
            }
        }

        logger.debug(String.valueOf(httpRequest));
        return httpRequest;
    }
}
