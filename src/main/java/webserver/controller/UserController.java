package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.utils.HttpRequestUtil;
import webserver.response.HttpResponse;
import webserver.service.UserService;
import webserver.utils.CookieUtil;

import java.util.HashMap;
import java.util.Map;

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

        HttpResponse httpResponse = new HttpResponse.Builder()
                .setHttpStatus(HttpStatus.FOUND)
                .setContentType("text/html;charset=utf-8")
                .setBody(HttpRequestUtil.getBody(redirectUrl))
                .build();

        httpResponse.getHeaders().put("Location", redirectUrl);

        return httpResponse;
    }

    public HttpResponse loginWithPOST(HttpRequest request) {
        String loginSuccessUrl = "../index.html";
        String loginFailureUrl = "login_failed.html";
        String redirectUrl;
        Map<String, String> cookieParameter = new HashMap<>();

        if (userService.login(request)) {
            redirectUrl = loginSuccessUrl;
            cookieParameter.put("logined", "true");
        } else {
            redirectUrl = loginFailureUrl;
            cookieParameter.put("logined", "false");
        }

        HttpResponse httpResponse = new HttpResponse.Builder()
                .setHttpStatus(HttpStatus.FOUND)
                .setContentType("text/html;charset=utf-8")
                .setBody(HttpRequestUtil.getBody(redirectUrl))
                .build();

        httpResponse.getHeaders().put("Location", redirectUrl);
        httpResponse.getHeaders().put("Set-Cookie", CookieUtil.setCookie(cookieParameter));

        System.out.println(httpResponse.getHeaders());

        return httpResponse;
    }
}
