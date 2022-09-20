package model;

import util.HttpRequestUtils;

import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public static User userOf(String url) {
        String queryStr;

        if (url.contains("\\?")) {
            queryStr = url.split("\\?")[0];
        } else {
            queryStr = url;
        }

        Map<String, String> parsedQueryStr = HttpRequestUtils.parseQueryString(queryStr);

        return new User(parsedQueryStr.get("userId"),
                parsedQueryStr.get("password"),
                parsedQueryStr.get("name"),
                parsedQueryStr.get("email"));
    }
}
