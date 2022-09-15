package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerUtilTest {

    @Test
    @DisplayName("Request로 부터 도메인을 확인할 수 있다.")
    void createHttpRequest() throws IOException {
        InputStream is = new ByteArrayInputStream("GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*".getBytes());

        String s = RequestUtil.getPath(new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).readLine());
        assertThat(s).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("getBody() 사용 시, 존재하는 파일의 경우 읽고 반환할 수 있다.")
    void readValidFile() throws IOException {
        InputStream is = new ByteArrayInputStream("GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*".getBytes());

        String s = new String(RequestUtil.getBody(is), StandardCharsets.UTF_8);

        // html 문서가 정상적으로 로드 되었는가?
        assertThat(s).contains("<!DOCTYPE html>");
    }

    @Test
    @DisplayName("getBody() 사용 시, 존재하지 않은 파일의 경우 Hello World를 표시한다.")
    void readInvalidFile() throws IOException {
        InputStream is = new ByteArrayInputStream("GET /notIndex.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*".getBytes());

        String s = new String(RequestUtil.getBody(is), StandardCharsets.UTF_8);

        assertThat(s).isEqualTo("Hello World");
    }
}
