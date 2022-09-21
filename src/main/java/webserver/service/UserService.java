package webserver.service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.enums.HttpMethod;
import webserver.exception.MethodNotSupportedException;
import webserver.request.HttpRequest;

import java.nio.charset.StandardCharsets;

public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private void create(String url) {
        User user = User.userOf(url);

        Database.addUser(user);
    }

    public void createUser(HttpRequest request) {
        HttpMethod httpMethod = request.getHttpMethod();

        if (httpMethod == HttpMethod.GET) {
            create(request.getDirectory());
        } else if (httpMethod == HttpMethod.POST) {
            create(new String(request.getBody(), StandardCharsets.UTF_8));
        } else {
            throw new MethodNotSupportedException();
        }
    }

    public boolean login(HttpRequest request) {
        String url = new String(request.getBody(), StandardCharsets.UTF_8);
        User user = User.userOf(url);
        User compareUser = Database.findUserById(user.getUserId());

        return compareUser != null &&
                compareUser.getPassword().equals(user.getPassword());
    }
}
