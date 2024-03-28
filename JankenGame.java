import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleBrowser extends JFrame {
    private JTextField urlField;
    private JEditorPane contentPane;

    public SimpleBrowser() {
        setTitle("Simple Browser");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        urlField = new JTextField();
        urlField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadURL(urlField.getText());
            }
        });

        contentPane = new JEditorPane();
        contentPane.setEditable(false);
        contentPane.setContentType("text/html");

        JScrollPane scrollPane = new JScrollPane(contentPane);

        getContentPane().add(urlField, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadURL(String url) {
        try {
            contentPane.setPage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleBrowser();
            }
        });
    }
}

class JankenGame {
    private enum Hand { ROCK, PAPER, SCISSORS }
    
    public static void main(String[] args) {
        System.out.println("Let's play Rock-Paper-Scissors!");

        while (true) {
            // Get user choice
            Hand userHand = getUserChoice();
            if (userHand == null) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            // Generate computer choice
            Hand computerHand = getRandomHand();

            // Display choices
            System.out.println("Your choice: " + userHand);
            System.out.println("Computer's choice: " + computerHand);

            // Determine the winner
            String result = determineWinner(userHand, computerHand);
            System.out.println(result);

            // Ask to play again
            System.out.println("Do you want to play again? (yes/no)");
            String playAgain = System.console().readLine();
            if (!playAgain.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Thanks for playing!");
    }

    private static Hand getUserChoice() {
        System.out.println("Enter your choice (rock, paper, or scissors):");
        String input = System.console().readLine().toLowerCase();

        switch (input) {
            case "rock":
                return Hand.ROCK;
            case "paper":
                return Hand.PAPER;
            case "scissors":
                return Hand.SCISSORS;
            default:
                return null;
        }
    }

    private static Hand getRandomHand() {
        int randomNum = (int) (Math.random() * 3);
        return Hand.values()[randomNum];
    }

    private static String determineWinner(Hand userHand, Hand computerHand) {
        if (userHand == computerHand) {
            return "It's a tie!";
        } else if ((userHand == Hand.ROCK && computerHand == Hand.SCISSORS) ||
                   (userHand == Hand.PAPER && computerHand == Hand.ROCK) ||
                   (userHand == Hand.SCISSORS && computerHand == Hand.PAPER)) {
            return "You win!";
        } else {
            return "Computer wins!";
        }
    }
}