package no.kristiania.maventesting;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getStatusCode());
    }
}
