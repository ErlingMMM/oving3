package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.SortedMap;
import java.util.TreeMap;

public class HttpClient {

    private final int statusCode;
    private final String body;
    private final SortedMap<String, String> headerFields = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);


    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);
        String request = "GET "+requestTarget+" HTTP/1.1\r\n"+
                "Host:"+host+"\r\n"+
                "Connection: close\r\n"+
                "\r\n";

        socket.getOutputStream().write(request.getBytes());


        String statusLine = readLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);


        String responseHeader;
        while (!((responseHeader = readLine(socket)).isBlank())){
           String[] headerField = responseHeader.split(":");
           headerFields.put(headerField[0].trim(), headerField[1].trim());
        }

        this.body = readLine(socket);
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

    public String getBody(){
        return body;
    }


    public int getStatusCode() {

        return statusCode;
    }


    public String getHeader(String field) {
        return headerFields.get(field);
    }

    public String getContentLength() {
        return headerFields.get("ContenT-length");
    }
}


