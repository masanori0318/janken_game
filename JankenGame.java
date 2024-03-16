import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.Scanner;

public class JankenGame {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new JankenHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is running on http://localhost:8000");
    }

    static class JankenHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html>"
                            + "<html lang='en'>"
                            + "<head>"
                            + "<meta charset='UTF-8'>"
                            + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                            + "<title>Rock Paper Scissors Game</title>"
                            + "<link rel='stylesheet' href='style.css'>"
                            + "</head>"
                            + "<body>"
                            + "<h1>Welcome to Rock Paper Scissors game!</h1>"
                            + "<div class='container'>"
                            + "<form id='gameForm' method='post'>"
                            + "<label for='choice'>Enter your choice (rock, paper, or scissors):</label><br>"
                            + "<input type='text' id='choice' name='choice'><br>"
                            + "<button type='submit'>Submit</button>"
                            + "</form>"
                            + "</div>"
                            + "<p id='result'></p>"
                            + "<script src='script.js'></script>"
                            + "</body>"
                            + "</html>";

            if ("post".equalsIgnoreCase(t.getRequestMethod())) {
                // リクエストボディからプレイヤーの選択を取得
                Scanner scanner = new Scanner(t.getRequestBody(), "UTF-8").useDelimiter("\\A");
                String playerChoice = scanner.hasNext() ? scanner.next().trim().toLowerCase() : "";
                scanner.close();

                Random random = new Random();
                String[] choices = {"rock", "paper", "scissors"};
                String computerChoice = choices[random.nextInt(choices.length)];

                String result;
                if (playerChoice.equals(computerChoice)) {
                    result = "It's a tie!";
                } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                           (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                           (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
                    result = "You win!";
                } else {
                    result = "Computer wins!";
                }

                // 結果をクライアントに送信
                response = response.replace("<p id='result'></p>", "<p id='result'>" + result + "</p>");
            }

            // CORSを有効にする
            t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            byte[] responseBytes = response.getBytes("UTF-8");
            t.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = t.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}