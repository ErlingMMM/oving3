package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {
    @Test
    void shouldReadResponseCode() throws IOException {
        HttpServer server = new HttpServer(10003 );
        int port = server.getActualPort();
        HttpClient client = new HttpClient("localhost", port, "/hello");
        HttpResponse response = client.executeRequest();
        //assertEquals(200, response.getResponseCode());
    }
}
