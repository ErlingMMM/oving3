import no.kristiania.maventesting.HttpClient;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class task3Test {



    @Test
    void shouldGetSuccessfulResponseCode() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatusCode());
    }


    @Test
    void shouldReadResponseHeaders() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader());
    }

    @Test
    void get404Error() throws IOException {
        HttpClient error404  = new HttpClient("httpbin.org", 80,"/nothing-here");
        assertEquals(404,error404.getStatusCode());
    }
}
