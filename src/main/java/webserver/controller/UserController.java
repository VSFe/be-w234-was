package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestUtil;
import webserver.response.HttpResponse;
import webserver.service.UserService;

public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public HttpResponse createUserWithGET(HttpRequest request) {
        String redirectUrl = "index.html";
        userService.createUser(request);

        return new HttpResponse.Builder()
                .setHttpStatus(HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setBody(HttpRequestUtil.getBody(redirectUrl))
                .build();
    }

    public HttpResponse createUserWithPOST(HttpRequest request) {
        String redirectUrl = "../index.html";
        userService.createUser(request);

        System.out.println("OK");

        HttpResponse httpResponse = new HttpResponse.Builder()
                .setHttpStatus(HttpStatus.FOUND)
                .setContentType("text/html;charset=utf-8")
                .setBody(HttpRequestUtil.getBody(redirectUrl))
                .build();

        httpResponse.getHeaders().put("Location", redirectUrl);

        return httpResponse;
    }
}
