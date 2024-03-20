import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new JankenHandler());
        server.setExecutor(null);
        server.start();
    }

    static class JankenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<html><body><h1>ジャンケンゲーム</h1><p>結果: " + playJanken() + "</p></body></html>";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String playJanken() {
            // ジャンケンのロジックをここに実装
            // この例では単純に"勝ち"、"負け"、"引き分け"のいずれかをランダムに返す
            String[] results = {"勝ち", "負け", "引き分け"};
            int randomIndex = (int) (Math.random() * results.length);
            return results[randomIndex];
        }
    }
}