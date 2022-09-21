package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.enums.HttpMethod;
import webserver.utils.HttpRequestUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String host;
    private String directory;
    private String MIMEType;
    private int contentLength;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private byte[] body;

    public HttpRequest() {
    }

    public HttpRequest(String directory, HttpMethod httpMethod) {
        this.directory = directory;
        this.httpMethod = httpMethod;
        this.headers = new HashMap<>();
    }

    public HttpRequest(String host, String directory, String MIMEType, int contentLength, HttpMethod httpMethod, byte[] body) {
        this.host = host;
        this.directory = directory;
        this.MIMEType = MIMEType;
        this.contentLength = contentLength;
        this.httpMethod = httpMethod;
        this.body = body;
        this.headers = new HashMap<>();
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

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public byte[] getBody() {
        return body;
    }

    private static String getHeaderName(String s) {
        return s.replace(" ", "").split(":")[0];
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "host='" + host + '\'' +
                ", directory='" + directory + '\'' +
                ", MIMEType='" + MIMEType + '\'' +
                ", httpMethod=" + httpMethod +
                ", parameters=" + parameters +
                ", body=" + body +
                ", headers=" + headers +
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
                case "Content-Length":
                    httpRequest.contentLength = Integer.parseInt(headerStr.split(" ")[1]);
                    break;
                default:
                    String[] headerPair = headerStr.replace(" ", "").split(":");
                    httpRequest.headers.put(headerPair[0], headerPair[1]);
            }
        }

        if (httpRequest.contentLength > 0) {
            httpRequest.body = IOUtils.readData(br, httpRequest.contentLength).getBytes();
        }

        logger.debug(String.valueOf(httpRequest));
        return httpRequest;
    }
}
