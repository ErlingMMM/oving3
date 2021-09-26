package no.kristiania.maventesting;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer extends Thread {
    private static final String WEB_ROOT = "src/public";
    private static final Pattern HTTP_LINE = Pattern.compile(
            "([A-Z]+) +(.*) +HTTP/([0-9]+\\.[0-9]+)",
            Pattern.CASE_INSENSITIVE);
    final String CRLF = "\r\n";
    private final SortedMap<String, String> headerFields = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    ServerSocket server;

    public HttpServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    @Override
    public void run() {

        while (server.isBound() || !server.isClosed()) {
            try {
                Date date = new Date();
                Socket socket = server.accept();
                OutputStream out = socket.getOutputStream();

                /**
                 * Fix way to get method
                 * Currently i believe socket gets digested, error occurs
                 */
                String line = readHttpFirsLine(socket);
                String target = getHttpProperties(line, "target");
                String method = getHttpProperties(line, "method");



                if (method.equals("POST")) new WriteToFile().writeToText(readHeaderFields(socket));

                if (target.equals("/")) target = "/index.html";

                boolean fileExists = new File(WEB_ROOT + target).isFile();

                if (fileExists) {
                    File file = new File(WEB_ROOT + target);

                    String result = getHtml(file);
                    writeHttpResponse(date, target, out, file, result);

                } else write404Response(out);

                out.close();
                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private String readHeaderFields(Socket socket) throws IOException {
        String responseHeader;
        while (!((responseHeader = readHttpFirsLine(socket)).isBlank())) {
            String[] headerField = responseHeader.split(":");
            headerFields.put(headerField[0].trim(), headerField[1].trim());
        }


        HttpPost postRead = new HttpPost(socket, headerFields.get("content-length"));
        postRead.start();

        String waitForPostBodyRead;
        while ((waitForPostBodyRead = postRead.getBody()) == null){}

        return postRead.getBody();
    }

    private String getHtml(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder result = new StringBuilder();

        int c;
        while ((c = reader.read()) != -1) {
            result.append((char) c);
        }
        return result.toString();
    }


    private void writeHttpResponse(Date date, String target, OutputStream out, File file, String result) throws IOException {
        String contentType = target.split("\\.")[1];

        String response = "HTTP/1.1 200 OK" + CRLF +
                "Date:" + date + CRLF +
                "Content-Length:" + file.length() + CRLF +
                "content-type: text/" + contentType + "; charset=utf-8" + CRLF +
                CRLF +
                result +
                CRLF + CRLF;

        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private String getHttpProperties(String line, String property) throws IOException {
        Matcher matcher = HTTP_LINE.matcher(line);
        if (!matcher.matches()) {
            throw new IOException("Invalid HTTP line");
        }
        switch (property) {
            case "method" -> {
                return matcher.group(1).toUpperCase();
            }
            case "target" -> {
                return matcher.group(2);
            }
            case "version" -> {
                return matcher.group(3);
            }
            default -> throw new IOException("Http property not found");
        }
    }


    private String readHttpFirsLine(Socket socket) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();
        int c;
        while ((c = in.read()) != '\r') {
            result.append((char) c);

        }

        return result.toString();
    }

    private void write404Response(OutputStream out) throws IOException {

        String notFoundHtml = getHtml(new File(WEB_ROOT + "/404.html"));

        String response = "HTTP/1.1 404 Not Found" + CRLF +
                "Date:" + LocalDateTime.now() + CRLF +
                "Content-Length:" + notFoundHtml.getBytes(StandardCharsets.UTF_8).length + CRLF +
                "content-type: text/html; charset=utf-8" + CRLF +
                CRLF +
                notFoundHtml +
                CRLF + CRLF;

        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}


