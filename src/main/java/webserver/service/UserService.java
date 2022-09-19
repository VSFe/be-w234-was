package webserver.service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void create(HttpRequest request) {
        String url = request.getDirectory();
        User user = User.userOf(url);

        Database.addUser(user);
    }
}
