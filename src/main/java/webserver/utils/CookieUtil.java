package webserver.utils;

import webserver.request.HttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieUtil {
    public static String setCookie(Map<String, String> parameters) {
        return parameters.entrySet()
                .stream()
                .map(pair -> pair.getKey() + "=" + pair.getValue())
                .collect(Collectors.joining(";")) + "; Path=/";
    }

    public static Map<String, String> getCookies(HttpRequest httpRequest) {
        if (!httpRequest.getParameters().containsKey("Set-Cookie")) {
            return new HashMap<>();
        }

        return Arrays.stream(httpRequest.getParameters().get("Set-Cookie")
                        .replace(" ", "")
                        .split(":")[1]
                        .split(";"))
                        .map(str -> str.split("="))
                        .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }
}
