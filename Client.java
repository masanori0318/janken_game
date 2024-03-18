import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverHostname = "localhost";
        int port = 8000;

        try {
            Socket socket = new Socket(serverHostname, port);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String userInputLine;
            while ((userInputLine = userInput.readLine()) != null) {
                out.write(userInputLine.getBytes());
                System.out.println("Server response: " + in.readLine());
            }

            userInput.close();
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
