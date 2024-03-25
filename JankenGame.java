import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleJankenBrowser {

    public static void main(String[] args) throws Exception {
        // ローカルホストで8000ポートを使用してHTTPサーバーを作成
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // /playエンドポイントにリクエストがあった場合のハンドラを設定
        server.createContext("/play", new JankenHandler());
        
        // サーバーを開始
        server.start();
        
        System.out.println("Server started on port 8000");
    }

    static class JankenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // ジャンケンの手を選択
            String[] hands = {"Rock", "Paper", "Scissors"};
            Random random = new Random();
            int index = random.nextInt(hands.length);
            String computerHand = hands[index];

            // レスポンスの内容を設定
            String response = "<html><head><title>Janken Game</title></head><body>"
                    + "<h1>Welcome to Janken Game!</h1>"
                    + "<p>Computer chose: " + computerHand + "</p>"
                    + "<p><a href=\"/play\">Play again</a></p>"
                    + "</body></html>";

            // HTTPレスポンスの設定
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}