package no.kristiania.maventesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpPost extends Thread {
    Socket socket;
    String body;
    int contentLength;
    InputStream in;

    public HttpPost(Socket socket, String contentLength) throws IOException {
        this.socket = socket;
        this.contentLength = Integer.parseInt(contentLength);
        in = socket.getInputStream();
    }

    public void run() {
        try {
            readBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readBody() throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength + 1; i++) {
            buffer.append((char) socket.getInputStream().read());
        }
        this.body = buffer.toString();
    }

    public String getBody() {
        return body;
    }
}
