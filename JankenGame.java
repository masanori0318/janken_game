import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SimpleJankenGame {

    public static void main(String[] args) throws IOException {
        int port = 8000; // ポート番号
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // リクエストを処理
            handleRequest(clientSocket);

            clientSocket.close();
            System.out.println("Client disconnected.");
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();

        String response = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n"
                        + "<html><body>"
                        + "<h1>ジャンケンゲーム</h1>"
                        + "<form method='post' action='/play'>"
                        + "<button type='submit' name='choice' value='rock'>岩</button>"
                        + "<button type='submit' name='choice' value='paper'>紙</button>"
                        + "<button type='submit' name='choice' value='scissors'>はさみ</button>"
                        + "</form>"
                        + "</body></html>";

        outputStream.write(response.getBytes());
    }

    public static String playJanken(String playerChoice) {
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        int computerIndex = random.nextInt(choices.length);
        String computerChoice = choices[computerIndex];

        // ゲームの結果を決定
        if (playerChoice.equals(computerChoice))
            return "引き分けです！";
        else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                 (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                 (playerChoice.equals("scissors") && computerChoice.equals("paper")))
            return "あなたの勝ちです！";
        else
            return "あなたの負けです！";
    }
}
