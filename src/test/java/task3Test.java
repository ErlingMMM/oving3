import no.kristiania.maventesting.HttpClient;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.assertEquals;

public class task3Test {



    @Test
    void shouldGetSuccessfulResponseCode() {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatusCode());
    }




}
