package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.enums.HttpMethod;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestUtil;
import webserver.response.HttpResponse;

import java.io.IOException;

public class ControllerResolver {
    private static Logger logger = LoggerFactory.getLogger(ControllerResolver.class);
    private UserController userController;

    public ControllerResolver() {
        userController = new UserController();
    }

    public HttpResponse resolve(HttpRequest httpRequest) throws IOException {
        String url = httpRequest.getDirectory().split("\\?")[0];
        HttpMethod method = httpRequest.getHttpMethod();
        logger.debug(url);

        if (method == HttpMethod.GET && url.equals("/user/create")) {
            return userController.createUserWithGET(httpRequest);
        }

        if (method == HttpMethod.POST && url.equals("/user/create")) {
            return userController.createUserWithPOST(httpRequest);
        }

        // 이후 다양한 Controller에 대한 Resolve
        return new HttpResponse.Builder()
                .setContentType(httpRequest.getMIMEType() + ";charset=utf-8")
                .setHttpStatus(HttpStatus.OK)
                .setBody(HttpRequestUtil.getBody(httpRequest.getDirectory()))
                .build();
    }
}
