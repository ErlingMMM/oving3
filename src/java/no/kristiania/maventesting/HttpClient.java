package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpClient {

    private final int statusCode;


    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);
        String request = "GET "+requestTarget+" HTTP/1.1\r\n"+
                "Host:"+host+"\r\n"+
                "Connection: close\r\n"+
                "\r\n";

        socket.getOutputStream().write(request.getBytes());

        StringBuilder result = new StringBuilder();
        String myResponseMessage = result.toString();
        System.out.println(myResponseMessage);
        String statusLine = readFirstLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);
    }

    private String readFirstLine(Socket socket) throws IOException {
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


