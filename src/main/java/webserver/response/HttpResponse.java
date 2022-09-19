package webserver.response;

import webserver.enums.HttpStatus;

public class HttpResponse {
    private String protocol = "HTTP/1.1";
    private HttpStatus httpStatus;
    private String contentType;
    private Integer contentLength;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, String contentType, Integer contentLength, byte[] body) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = body;
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getContentType() {
        return contentType;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public byte[] getBody() {
        return body;
    }

    public static class Builder {
        private HttpStatus httpStatus;
        private String contentType;
        private byte[] body;
        private Integer contentLength;

        public Builder() {
        }

        public Builder setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setBody(byte[] body) {
            this.body = body;
            this.contentLength = body.length;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(
                    this.httpStatus,
                    this.contentType,
                    this.contentLength,
                    this.body
            );
        }
    }
}
