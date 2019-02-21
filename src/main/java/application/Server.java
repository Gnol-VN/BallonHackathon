package application;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
    static int LeaderBoardscore = 0;
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/increase", new MyHandlerIncrease());
        server.createContext("/decrease", new DecreaseHandler());
        server.createContext("/get", new getHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandlerIncrease implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            LeaderBoardscore++;
            String response =String.valueOf(LeaderBoardscore);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class DecreaseHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            LeaderBoardscore--;
            String response =String.valueOf(LeaderBoardscore);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class getHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = String.valueOf(LeaderBoardscore);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
