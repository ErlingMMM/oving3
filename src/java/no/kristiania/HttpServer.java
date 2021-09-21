package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private final ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
         new Thread(this::handleConnections).start();
        System.out.println("New thread created, running handleConnection");
    }



    private void handleConnections() {
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8020);
        Socket socket = serverSocket.accept();

        String messageBody = "Hello world";

        String responseMessage = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + messageBody.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                messageBody;

        socket.getOutputStream().write(responseMessage.getBytes());

        int c;
        while((c=socket.getInputStream().read())!=-1){
            System.out.print((char)c);
        }
    }

    public int getActualPort() {
     return serverSocket.getLocalPort();
    }


}
