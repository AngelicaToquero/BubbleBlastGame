package Frames;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 20);
    JLabel label = new JLabel("Welcome to the Bubble Sort Game!");
    JLabel instructionLabel = new JLabel("Instructions: Should the highlighted numbers be swapped?");
    JLabel timeLabel = new JLabel("Time: 0 seconds");

    // For main panel
    JPanel mainPanel = new JPanel(new GridLayout(5, 1)); // GridLayout with 4 rows and 1 column

    JButton yesButton;
    JButton noButton;
    JButton hintButton; // New hint button

    private static final int ARRAY_SIZE = 10;
    private static final int MAX_NUMBER = 100;
    private static final int PENALTY = 5;

    private int[] numbers;
    private int penaltyTime = 0;
    private long startTime;

    CircleShape[] numberShapes = new CircleShape[ARRAY_SIZE];

    private int lastProcessedIndex = 0; // Initialize the last processed index
    private int swapCount = 0;
    private Timer inactivityTimer;
    private Timer timer;
    private boolean gameEnded = false; // Flag to track game state
    private JOptionPane hintOptionPane;

    GameFrame() {
        // Main panel settings
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, -5)); // GridLayout with 3 rows, 1 column, and 10px
        // horizontal and vertical gap
        mainPanel.setBackground(new Color(255, 205, 234));

        // Create a panel for label, instruction, and time
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(new Color(255, 205, 234));

        // Add labels to the info panel
        infoPanel.add(label);
        infoPanel.add(instructionLabel);
        infoPanel.add(timeLabel);

        label.setFont(font);
        instructionLabel.setFont(font); // Increase font size to 16
        timeLabel.setFont(font); // Increase font size to 16
        // Add the info panel to the main panel
        mainPanel.add(infoPanel);

        // Add circles to main panel
        JPanel circlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // FlowLayout with center alignment
        // and 10px horizontal and vertical
        // gap
        circlePanel.setBackground(new Color(255, 205, 234));

        for (int i = 0; i < ARRAY_SIZE; i++) {
            numberShapes[i] = new CircleShape();
            circlePanel.add(numberShapes[i]);
        }

        mainPanel.add(circlePanel);

        // Add a panel for buttons to main panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50)); // FlowLayout with center alignment
        // and 10px horizontal and vertical
        // gap
        buttonPanel.setBackground(new Color(255, 205, 234));

        // Add yes button
        yesButton = new JButton("Yes");
        yesButton.setPreferredSize(new Dimension(150, 60)); // Set preferred size for Yes button
        yesButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(yesButton);

        // Add no button
        noButton = new JButton("No");
        noButton.setPreferredSize(new Dimension(150, 60)); // Set preferred size for No button
        noButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(noButton);

        // Add hint button to the east (right) side of BorderLayout
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // FlowLayout for hint button
        rightButtonPanel.setBackground(new Color(255, 205, 234));
        rightButtonPanel.add(hintButton = new JButton("?"));
        hintButton.setPreferredSize(new Dimension(40, 40)); // Set preferred size for hint button
        hintButton.setMargin(new Insets(0, 0, 0, 0)); // Set margin to zero for a round button
        hintButton.setFocusPainted(false); // Remove focus paint
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setForeground(Color.RED);
        hintButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for the button text
        hintButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(rightButtonPanel, BorderLayout.EAST); // Add hint button to the right side

        mainPanel.add(buttonPanel);

        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        hintButton.addActionListener(this); // ActionListener for hint button

        // Add main panel to the frame
        add(mainPanel);

        // Initialize inactivity timer
        inactivityTimer = new Timer(10000, new ActionListener() { // 10 seconds timer
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameEnded && getExtendedState() != ICONIFIED) { // Check if the game has not ended and window is
                                                                     // not minimized
                    showRandomHint();
                }
            }
        });
        inactivityTimer.setRepeats(false); // Only trigger once

        // Add component listener to detect frame state changes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getExtendedState() == ICONIFIED) { // Check if the frame is minimized
                    inactivityTimer.stop(); // Stop the inactivity timer
                }
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if (getExtendedState() != ICONIFIED) { // Check if the frame is not minimized
                    inactivityTimer.restart(); // Restart the inactivity timer
                }
            }
        });

        // Frame settings
        setTitle("Bubble Sort Game");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        initializeGame();
        displayNumbers();
        startTime = System.currentTimeMillis();
        updateTimer();
    }

    private void initializeGame() {
        numbers = generateRandomArray();
    }

    private int[] generateRandomArray() {
        Random random = new Random();
        int[] array = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(MAX_NUMBER);
        }
        return array;
    }

    private void displayNumbers() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (i == lastProcessedIndex || i == lastProcessedIndex + 1) {
                numberShapes[i].setColor(Color.WHITE);
            } else {
                numberShapes[i].setColor(Color.PINK);
            }
            numberShapes[i].setText(Integer.toString(numbers[i]));
        }
    }

    private void updateTimer() {
        // Stop the existing timer if it's running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(1000, new ActionListener() {
            int secondsElapsed = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameEnded) {
                    secondsElapsed++;
                    timeLabel.setText("Time: " + secondsElapsed + " seconds");
                }
            }
        });

        // Start the timer only if the game is not ended
        if (!gameEnded) {
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            swapNumbers();
        } else if (e.getSource() == noButton) {
            lastProcessedIndex++; // Move one index forward
            displayArrayWithHighlight(); // Update the display

            // Check if it's the last pass, prompt for another pass
            if (lastProcessedIndex >= ARRAY_SIZE - 1) {
                promptForNextPass();
            } else {
                bubbleSort(); // Continue sorting after penalty
            }
        } else if (e.getSource() == hintButton) { // Handle hint button click
            if (!gameEnded) { // Check if the game has not ended
                showRandomHint();
            }
        }
    }

    private void bubbleSort() {
        // Update the instruction label
        instructionLabel.setText("Instructions: Should the highlighted numbers be swapped?");

        // Display the array with highlight
        displayArrayWithHighlight();
        if (lastProcessedIndex >= ARRAY_SIZE - 1) {
            promptForNextPass();
        }
        swapCount++;
    }

    private boolean isSorted() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private void swapNumbers() {
        int[] tempArray = numbers.clone();
        int[] indices = getHighlightedIndices(); // Get the next pair of indices to highlight
        int temp = numbers[indices[0]];
        numbers[indices[0]] = numbers[indices[1]];
        numbers[indices[1]] = temp;

        lastProcessedIndex = indices[1]; // Update the last processed index

        displayArrayWithHighlight(); // Update the display

        if (Arrays.equals(numbers, tempArray)) {
            penaltyTime += PENALTY;
        }
        if (lastProcessedIndex >= ARRAY_SIZE - 1) {
            promptForNextPass();
        }
    }

    private void displayArrayWithHighlight() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (i == lastProcessedIndex || i == lastProcessedIndex + 1) {
                numberShapes[i].setColor(Color.WHITE);
            } else {
                numberShapes[i].setColor(Color.pink);
            }
            numberShapes[i].setText(Integer.toString(numbers[i]));
        }
    }

    private void promptForNextPass() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to perform another pass?", "Next Pass",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            lastProcessedIndex = 0; // Reset the lastProcessedIndex
            bubbleSort(); // Perform another pass
        } else {
            if (isSorted()) {
                long endTime = System.currentTimeMillis();
                double timeTaken = (endTime - startTime + penaltyTime) / 1000;
                int option = JOptionPane.showOptionDialog(mainPanel,
                        "Congratulations! You sorted the array in " + timeTaken + " seconds with " + swapCount
                                + " swaps.",
                        "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[] { "Play Again", "Quit" }, "Play Again");

                if (option == 0) {
                    initializeGame(); // Start a new game
                    displayNumbers();
                    startTime = System.currentTimeMillis();
                    swapCount = 0;
                    penaltyTime = 0;
                    lastProcessedIndex = 0;
                    updateTimer();
                } else {
                    // Quit the game
                    System.exit(0);
                }
            } else {
                // Check if it's the last pass, prompt for another pass
                if (lastProcessedIndex >= ARRAY_SIZE - 1) {
                    int option = JOptionPane.showOptionDialog(mainPanel, "Sorry, you lose. The array is not sorted.",
                            "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
                            new String[] { "Try Again", "Quit" }, "Try Again");

                    if (option == 0) {
                        initializeGame(); // Reset the game
                        displayNumbers();
                        startTime = System.currentTimeMillis();
                        swapCount = 0;
                        penaltyTime = 0;
                        lastProcessedIndex = 0;
                        updateTimer();
                    } else {
                        // Quit the game
                        System.exit(0);
                    }
                } else {
                    bubbleSort(); // Continue sorting
                }
            }
        }
    }

    private int[] getHighlightedIndices() {
        int[] indices = new int[2];
        indices[0] = lastProcessedIndex;
        indices[1] = lastProcessedIndex + 1;

        return indices;
    }

    private void showRandomHint() {
        if (hintOptionPane != null && hintOptionPane.isVisible()) {
            hintOptionPane.setVisible(false);

            // Check if the window is minimized or the game is ended
            if (getExtendedState() == ICONIFIED || gameEnded) {
                return; // Do not display hint if window is minimized or game is ended
            }
        }

        String[] hints = {
                "Keep an eye on the numbers inside the bubbles to guide your sorting.",
                "Start with the first pair of bubbles and continue swapping until they're in order.",
                "Swap adjacent bubbles to arrange them in ascending order.",
                "Visualize the bubbles moving and prioritize sorting the smaller numbers first.",
                "Take your time and strategize each swap to minimize the number of movements needed.",
                "Remember, patience and methodical thinking are key to mastering bubble sorting.",
                "Alright, champ! Think of it like organizing your messy desk - gotta move things around until everything's neat and tidy!",
                "Hey buddy, wanna tackle this like a puzzle? Just slide those bubbles like you're solving a Rubik's Cube!",
                "Hey pal, ever played Tetris? It's kinda like that, but with numbers inside bubbles. Just gotta fit 'em in the right order!",
                "Okay, let's sort these bubbles out! It's like cleaning up your room - start with the messiest corner and work your way through!",
                "Yo, ready to dive into some bubble fun? Think of it as a little challenge, like trying to beat your high score in a game!",
                "Hey friend! Sorting these bubbles is like solving a riddle - piece by piece until it all clicks into place!",
                "Alright, time to play bubble boss! Imagine you're directing a traffic jam - gotta shuffle those numbers until they're moving smoothly!",
                "Hey, wanna crack this bubble code? It's like cracking a safe, but with numbers inside bubbles. Let's find the right combo!",
                "Hey there, champ! Let's get these bubbles in line, like herding cats but with numbers. You got this!"
        };

        Random random = new Random();
        int index = random.nextInt(hints.length);

        // Set the background color for the JOptionPane dialog
        UIManager.put("OptionPane.background", new Color(255, 240, 245));
        UIManager.put("Panel.background", new Color(255, 240, 245));

        // Show the hint message in a JOptionPane
        JOptionPane.showMessageDialog(this, hints[index], "Hint", JOptionPane.INFORMATION_MESSAGE);

        // Reset the background color to default after displaying the message
        UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
        UIManager.put("Panel.background", UIManager.get("Panel.background"));
    }

    class CircleShape extends JPanel {
        private Color color = Color.PINK;
        private String text = "";

        CircleShape() {
            setPreferredSize(new Dimension(100, 100)); // Set the size of the circle
        }

        void setColor(Color color) {
            this.color = color;
            repaint(); // Redraw the shape with the new color
        }

        void setText(String text) {
            this.text = text;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(255, 205, 234));
            g.setColor(color);
            int circleSize = 100; // Change the size of the circle
            int x = (getWidth() - circleSize) / 2;
            int y = (getHeight() - circleSize) / 2;
            g.fillOval(x, y, circleSize, circleSize);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, circleSize, circleSize);
            Font font = g.getFont().deriveFont(Font.BOLD, 16); // Adjust the font size here (16 is just an example)
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            g.drawString(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4);
        }
    }
}