import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SimpleHttpServer {

    public static void main(String[] args) {
        int port = 8000; // ポート番号
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("HTTPサーバーがポート " + port + " で起動しました。");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("クライアントが接続しました。");

                OutputStream outputStream = clientSocket.getOutputStream();

                // HTTPレスポンスを送信
                String response = generateResponse();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + response;
                outputStream.write(httpResponse.getBytes("UTF-8"));

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateResponse() {
        String[] choices = {"グー", "チョキ", "パー"};
        Random random = new Random();
        int computerChoiceIndex = random.nextInt(choices.length);
        String computerChoice = choices[computerChoiceIndex];

        String result = playGame(computerChoice);
        return "<h1>ジャンケンゲーム</h1>"
             + "<p>コンピュータの選択：" + computerChoice + "</p>"
             + "<p>" + result + "</p>";
    }

    private static String playGame(String computerChoice) {
        String playerChoice = "グー"; // 仮のプレイヤーの選択

        if (playerChoice.equals(computerChoice)) {
            return "引き分け！";
        } else if ((playerChoice.equals("グー") && computerChoice.equals("チョキ")) ||
                   (playerChoice.equals("チョキ") && computerChoice.equals("パー")) ||
                   (playerChoice.equals("パー") && computerChoice.equals("グー"))) {
            return "勝ち！";
        } else {
            return "負け！";
        }
    }
}