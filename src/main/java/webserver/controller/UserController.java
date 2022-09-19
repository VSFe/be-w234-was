package webserver.controller;

import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestUtil;
import webserver.response.HttpResponse;
import webserver.service.UserService;

import java.io.IOException;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public HttpResponse createUser(HttpRequest request) throws IOException {
        String redirectUrl = "index.html";
        userService.create(request);

        return new HttpResponse.Builder()
                .setHttpStatus(HttpStatus.OK)
                .setContentType("text/html;charset=utf-8")
                .setBody(HttpRequestUtil.getBody(redirectUrl))
                .build();
    }
}
