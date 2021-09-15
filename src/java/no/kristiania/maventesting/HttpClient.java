package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpClient {

    private final int statusCode;
    private String header = null;


    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);
        String request = "GET "+requestTarget+" HTTP/1.1\r\n"+
                "Host:"+host+"\r\n"+
                "Connection: close\r\n"+
                "\r\n";

        socket.getOutputStream().write(request.getBytes());


        String statusLine = readFirstLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);


        String responseHeader;
        while (!((responseHeader = readFirstLine(socket)).isBlank())){
           String[] arr = responseHeader.split(":");
            if (arr[0].trim().equals("Content-Type")){
                this.header = arr[1].trim();
            }
        }
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




    public String getHeader() {
        return header;
    }
}


