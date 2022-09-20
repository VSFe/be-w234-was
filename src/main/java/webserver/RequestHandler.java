package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.ControllerResolver;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private ControllerResolver controllerResolver;
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.controllerResolver = new ControllerResolver();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequest.requestInfoOf(in);
            HttpResponse response = controllerResolver.resolve(httpRequest);

            responseHeader(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            HttpStatus httpStatus = httpResponse.getHttpStatus();

            dos.writeBytes(httpResponse.getProtocol() + " ");
            dos.writeBytes(httpStatus.getCode() + " " );
            dos.writeBytes(httpStatus.getMessage() + "\r\n");
            dos.writeBytes("Content-Type: " + httpResponse.getContentType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + httpResponse.getContentLength() + "\r\n");

            for (var header : httpResponse.getHeaders().entrySet()) {
                dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.write(httpResponse.getBody(), 0, httpResponse.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
