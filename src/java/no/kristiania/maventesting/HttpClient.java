package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpClient {

    public HttpClient(String host, int port, String requestTarget) {

    }

    public static void main(String[] args) throws IOException {
        /*Socket socket = new Socket("urlecho.appspot.com", 80);
        String request = "GET /html HTTP/1.1 \r\n "+
        "Host: httpbin.org\r\n"+
        "Connection: keep-alive\r\n"+
        "Cache-Control: max-age=0\r\n"+
        "Upgrade-Insecure-Requests: 1\r\n\r\n";*/

        Socket socket = new Socket("httpbin.org", 80);
        socket.getOutputStream().write(
                ("GET /html HTTP/1.1 \r\n "+
                "Host: httpbin.org\r\n"+
                "\r\n").getBytes()
        );

        /*socket.getOutputStream().write(request.getBytes());*/

        InputStream in = socket.getInputStream();
        int c;
        while ( (c = in.read())!= -1){
            System.out.println((char) c);
        }
        /*while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char) c);
        }*/


    }

    public int getStatusCode() {
        return 200;
    }


    /*public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client);
    }*/
}


