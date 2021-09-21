import no.kristiania.maventesting.HttpClient;
import no.kristiania.maventesting.HttpServer;
import org.junit.jupiter.api.*;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class task3Test {

@BeforeAll
static void init() throws IOException {
    HttpServer server = new HttpServer(8080);
    server.start();
}

    @Test
     void shouldGetSuccessfulResponseCode() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals(200, client.getStatusCode());
    }


    @Test
    void shouldReadResponseHeaders() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void get404Error() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/nothing-here");
        assertEquals(404,client.getStatusCode());
    }

    @Test
    void shouldReadResponseBodyContentLength() throws IOException {
        HttpClient client = new HttpClient("localhost", 8080, "/");
        assertEquals("3766", client.getContentLength());
    }

}
