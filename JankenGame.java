import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleHTTPServer {

    public static void main(String[] args) throws IOException {
        int port = 8000; // ポート番号

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is listening on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // リクエストを処理
            handleRequest(socket);
        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        try (OutputStream outputStream = socket.getOutputStream()) {
            String htmlResponse = "<html><head><title>Janken Game</title></head><body>"
                    + "<h1>Welcome to Janken Game</h1>"
                    + "<p>Make your choice:</p>"
                    + "<button onclick=\"play('rock')\">Rock</button>"
                    + "<button onclick=\"play('paper')\">Paper</button>"
                    + "<button onclick=\"play('scissors')\">Scissors</button>"
                    + "<p id=\"result\"></p>"
                    + "<script>"
                    + "function play(playerChoice) {"
                    + "    var choices = ['rock', 'paper', 'scissors'];"
                    + "    var computerChoice = choices[Math.floor(Math.random() * choices.length)];"
                    + "    var result = '';"
                    + "    if (playerChoice === computerChoice) {"
                    + "        result = 'It\'s a tie!';"
                    + "    } else if ((playerChoice === 'rock' && computerChoice === 'scissors') ||"
                    + "               (playerChoice === 'paper' && computerChoice === 'rock') ||"
                    + "               (playerChoice === 'scissors' && computerChoice === 'paper')) {"
                    + "        result = 'You win!';"
                    + "    } else {"
                    + "        result = 'Computer wins!';"
                    + "    }"
                    + "    document.getElementById('result').innerHTML = 'You chose ' + playerChoice + ', computer chose ' + computerChoice + '. ' + result;"
                    + "}"
                    + "</script>"
                    + "</body></html>";

            String httpResponse = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + htmlResponse.length() + "\r\n"
                    + "Connection: close\r\n\r\n";
            String response = httpResponse + htmlResponse;

            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}