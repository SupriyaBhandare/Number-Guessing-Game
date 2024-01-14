import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int targetNumber;
    private int maxAttempts = 5;
    private int attempts = 0;
    private int score = 100;

    private JTextField guessField;
    private JLabel resultLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        initializeGame();

        createTopPanel();
        createCenterPanel();
        createResultPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGame() {
        Random random = new Random();
        targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        attempts = 0;
        score = 100;
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel instructionLabel = new JLabel("Guess the number between " + lowerBound + " and " + upperBound);
        topPanel.add(instructionLabel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        centerPanel.add(guessField);
        centerPanel.add(guessButton);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void createResultPanel() {
        JPanel resultPanel = new JPanel(new GridLayout(3, 1));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultLabel = new JLabel("");
        attemptsLabel = new JLabel("");
        scoreLabel = new JLabel("");

        resultPanel.add(resultLabel);
        resultPanel.add(attemptsLabel);
        resultPanel.add(scoreLabel);

        add(resultPanel, BorderLayout.SOUTH);
    }

    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());

            if (userGuess == targetNumber) {
                resultLabel.setText("Congratulations! You guessed the number!");
                attemptsLabel.setText("Attempts: " + (attempts + 1));
                scoreLabel.setText("Score: " + score);

                int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    initializeGame();
                    guessField.setText("");
                    resultLabel.setText("");
                    attemptsLabel.setText("");
                    scoreLabel.setText("");
                } else {
                    System.exit(0);
                }
            } else if (userGuess < targetNumber) {
                resultLabel.setText("Too low! Try again.");
            } else {
                resultLabel.setText("Too high! Try again.");
            }

            attempts++;
            score -= 20;

            if (attempts == maxAttempts) {
                resultLabel.setText("Sorry, you've run out of attempts. The correct number was: " + targetNumber);

                int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    initializeGame();
                    guessField.setText("");
                    resultLabel.setText("");
                    attemptsLabel.setText("");
                    scoreLabel.setText("");
                } else {
                    System.exit(0);
                }
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new NumberGuessingGameGUI());
    }
}
