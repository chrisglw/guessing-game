import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class NumberGuessingGameGUI {
    private int secretNumber;
    private int maxAttempts;
    private int remainingAttempts;

    private JTextField inputField;
    private JButton submitButton;
    private JLabel messageLabel;

    public NumberGuessingGameGUI(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        this.remainingAttempts = maxAttempts;

        // Generate a random number between 1 and 100
        Random random = new Random();
        this.secretNumber = random.nextInt(100) + 1;

        // Create the Swing GUI components
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        inputField = new JTextField(10);
        submitButton = new JButton("Submit");
        messageLabel = new JLabel("Enter your guess and press Submit.");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(submitButton);

        frame.add(messageLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        // Enable submitting by pressing "Enter" key
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkGuess();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void checkGuess() {
        if (remainingAttempts > 0) {
            try {
                int guess = Integer.parseInt(inputField.getText());

                if (guess < 1 || guess > 100) {
                    messageLabel.setText("Please enter a number between 1 and 100.");
                } else {
                    remainingAttempts--;

                    if (guess < secretNumber) {
                        messageLabel.setText("Higher! Remaining attempts: " + remainingAttempts);
                    } else if (guess > secretNumber) {
                        messageLabel.setText("Lower! Remaining attempts: " + remainingAttempts);
                    } else {
                        messageLabel.setText("Congratulations! You've guessed the number: " + secretNumber);

                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            resetGame();
                        } else {
                            submitButton.setEnabled(false);
                        }
                    }

                    if (remainingAttempts == 0) {
                        int choice = JOptionPane.showConfirmDialog(null, "Out of attempts. Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            resetGame();
                        } else {
                            submitButton.setEnabled(false);
                        }
                    }

                    // Clear the input field
                    inputField.setText("");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid number.");
            }
        }
    }

    private void resetGame() {
        Random random = new Random();
        this.secretNumber = random.nextInt(100) + 1;
        this.remainingAttempts = maxAttempts;
        messageLabel.setText("Enter your guess and press Submit.");
        inputField.setText("");
        submitButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGameGUI(10);
            }
        });
    }
}
