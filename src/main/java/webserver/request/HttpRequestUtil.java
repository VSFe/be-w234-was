package webserver.request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpRequestUtil {
    public static byte[] getBody(HttpRequest httpRequest) throws IOException {
        try {
            String path = httpRequest.getDirectory();
            return Files.readAllBytes(new File("./webapp" + path).toPath());
        } catch (IOException e) {
            return "Hello World".getBytes();
        }
    }

    public static String getPath(String s) {
        return s.split(" ")[1];
    }
}
