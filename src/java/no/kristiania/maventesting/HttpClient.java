package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpClient {

    private final int statusCode;
    private final Map<String, String> headerFields = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);


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
           String[] headerField = responseHeader.split(":");
           headerFields.put(headerField[0].trim(), headerField[1].trim());
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


    public String getHeader(String field) {
        return headerFields.get(field);
    }

    public String getContentLength() {
        return headerFields.get("content-length");
    }

    //Missing getBody implementation
}


