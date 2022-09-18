package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestInfo {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
    private String host;
    private String directory;
    private String MIMEType;
    private HttpMethod httpMethod;

    public RequestInfo() {
    }

    public RequestInfo(String directory, HttpMethod httpMethod) {
        this.directory = directory;
        this.httpMethod = httpMethod;
    }

    public RequestInfo(String host, String directory, String MIMEType, HttpMethod httpMethod) {
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

    public static RequestInfo requestInfoOf(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String headerStr = br.readLine();
        String path = RequestUtil.getPath(headerStr);
        HttpMethod httpMethod = HttpMethod.valueOf(headerStr.split(" ")[0].toUpperCase());
        RequestInfo requestInfo = new RequestInfo(path, httpMethod);

        while ((headerStr = br.readLine()) != null && !"".equals(headerStr)) {
            logger.debug(headerStr);
            String headerName = getHeaderName(headerStr);

            switch (headerName) {
                case "Host":
                    requestInfo.host = headerStr.split(" ")[1];
                    break;
                case "Accept":
                    requestInfo.MIMEType = headerStr.split(" ")[1].split(",")[0];
                    break;
                default:
                    break;
            }
        }

        return requestInfo;
    }
}
