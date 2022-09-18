package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static byte[] getBody(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String path = getPath(br.readLine());
        String log = path;

        try {
            while (!"".equals(log) && log != null) {
                logger.debug(log);
                log = br.readLine();
            }

            return Files.readAllBytes(new File("./webapp" + path).toPath());
        } catch (IOException e) {
            return "Hello World".getBytes();
        }
    }

    public static String getPath(String s) {
        return s.split(" ")[1];
    }
}
