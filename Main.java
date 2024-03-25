import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class RockPaperScissorsServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port 8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("GET")) {
                handleGetRequest(exchange);
            }
        }

        private void handleGetRequest(HttpExchange exchange) throws IOException {
            String htmlResponse = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Rock Paper Scissors</title>"
                    + "</head>"
                    + "<body>"
                    + "<h1>Rock Paper Scissors</h1>"
                    + "<div id='game'>"
                    + "<div id='choices'>"
                    + "<button onclick='playGame(\"rock\")'>Rock</button>"
                    + "<button onclick='playGame(\"paper\")'>Paper</button>"
                    + "<button onclick='playGame(\"scissors\")'>Scissors</button>"
                    + "</div>"
                    + "<div id='result'></div>"
                    + "</div>"
                    + "<script>"
                    + "function playGame(playerSelection) {"
                    + "  var choices = ['Rock', 'Paper', 'Scissors'];"
                    + "  var computerSelection = choices[Math.floor(Math.random() * choices.length)];"
                    + "  var result = playRound(playerSelection, computerSelection);"
                    + "  document.getElementById('result').textContent = result;"
                    + "}"
                    + "function playRound(playerSelection, computerSelection) {"
                    + "  if (playerSelection === computerSelection) {"
                    + "    return 'It\\'s a tie!';"
                    + "  } else if ("
                    + "    (playerSelection === 'Rock' && computerSelection === 'Scissors') ||"
                    + "    (playerSelection === 'Paper' && computerSelection === 'Rock') ||"
                    + "    (playerSelection === 'Scissors' && computerSelection === 'Paper')"
                    + "  ) {"
                    + "    return 'You win! ' + playerSelection + ' beats ' + computerSelection + '.';"
                    + "  } else {"
                    + "    return 'You lose! ' + computerSelection + ' beats ' + playerSelection + '.';"
                    + "  }"
                    + "}"
                    + "</script>"
                    + "</body>"
                    + "</html>";

            exchange.sendResponseHeaders(200, htmlResponse.length());
            OutputStream os = exchange.getResponseBody();
            os.write(htmlResponse.getBytes());
            os.close();
        }
    }
}
