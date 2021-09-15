package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpClient {

    private final int statusCode;


    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);
        socket.getOutputStream().write(
                ("GET /html HTTP/1.1 \r\n "+
                        "Host: httpbin.org\r\n"+
                        "\r\n").getBytes()
        );

        StringBuilder result = new StringBuilder();
        String myResponseMessage = result.toString();
        System.out.println(myResponseMessage);
        String statusLine = readLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);
        //System.out.print(result);


    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();
        int c;
        while ( (c = in.read()) != '\r'  ){
            result.append((char) c);
        }
        return result.toString();
    }


    public int getStatusCode() {

        return statusCode;
    }



    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getStatusCode());
    }


    public String getHeader(String s) {
        return null;
    }
}


