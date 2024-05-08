package Frames;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 24);
    JLabel label = new JLabel("Welcome to the Bubble Sorting Challenge!");
    JLabel instructionLabel = new JLabel("Should the highlighted numbers be swapped?");
    JLabel timeLabel = new JLabel("Time: 0 seconds");

    JPanel mainPanel = new JPanel(new GridLayout(5, 1));

    JButton yesButton;
    JButton noButton;
    JButton hintButton; 

    private static final int ARRAY_SIZE = 10;
    private static final int MAX_NUMBER = 100;
    private static final int PENALTY = 5;

    private int[] numbers;
    private int penaltyTime = 0;
    private long startTime;

    CircleShape[] numberShapes = new CircleShape[ARRAY_SIZE];

    private int lastProcessedIndex = 0;
    private int swapCount = 0;
    private Timer inactivityTimer;
    private Timer timer;
    private boolean gameEnded = false;
    private JOptionPane hintOptionPane;

    GameFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, -5));
        mainPanel.setBackground(new Color(255, 205, 234));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(new Color(255, 205, 234));

        infoPanel.add(label);
        infoPanel.add(instructionLabel);
        infoPanel.add(timeLabel);

        label.setFont(font);
        instructionLabel.setFont(font); 
        timeLabel.setFont(font); 
        mainPanel.add(infoPanel);

        JPanel circlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); 
        circlePanel.setBackground(new Color(255, 205, 234));

        for (int i = 0; i < ARRAY_SIZE; i++) {
            numberShapes[i] = new CircleShape();
            circlePanel.add(numberShapes[i]);
        }

        mainPanel.add(circlePanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50)); 
        buttonPanel.setBackground(new Color(255, 205, 234));

        yesButton = new JButton("Yes");
        yesButton.setPreferredSize(new Dimension(150, 60)); 
        yesButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(yesButton);

        noButton = new JButton("No");
        noButton.setPreferredSize(new Dimension(150, 60)); 
        noButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(noButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setBackground(new Color(255, 205, 234));
        rightButtonPanel.add(hintButton = new JButton("?"));
        hintButton.setPreferredSize(new Dimension(40, 40));
        hintButton.setMargin(new Insets(0, 0, 0, 0)); 
        hintButton.setFocusPainted(false); 
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setForeground(Color.RED);
        hintButton.setFont(new Font("Arial", Font.BOLD, 20)); 
        hintButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(rightButtonPanel, BorderLayout.EAST); 

        mainPanel.add(buttonPanel);

        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        hintButton.addActionListener(this); 

        add(mainPanel);

        inactivityTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameEnded && getExtendedState() != ICONIFIED) {
                                                                     
                    showRandomHint();
                }
            }
        });
        inactivityTimer.setRepeats(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getExtendedState() == ICONIFIED) {
                    inactivityTimer.stop();
                }
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if (getExtendedState() != ICONIFIED) {
                    inactivityTimer.restart();
                }
            }
        });

        setTitle("endOfPass Blast Game");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        initializeGame();
        displayArrayWithHighlight();
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

    private void updateTimer() {
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
        if (!gameEnded) {
            timer.start();
        }
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == yesButton) {
        swapNumbers();
    } else if (e.getSource() == noButton) {
        boolean shouldNotSwap = numbers[lastProcessedIndex] > numbers[lastProcessedIndex + 1];
    
        if (shouldNotSwap) {
            JOptionPane.showMessageDialog(this, "Numbers " + numbers[lastProcessedIndex] + " and " + numbers[lastProcessedIndex + 1] + " should be swapped.",
                    "Swap Needed", JOptionPane.WARNING_MESSAGE);
                    penaltyTime += 5000;
            displayArrayWithHighlight();
        } else {
            lastProcessedIndex++;
            displayArrayWithHighlight();
            endOfPass();
        }
    } else if (e.getSource() == hintButton) {
        if (!gameEnded) {
            showRandomHint();
        }
    }
}    

    private void endOfPass() {
        displayArrayWithHighlight();
        if (lastProcessedIndex >= ARRAY_SIZE - 1) {
            promptForNextPass();
        }
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

        boolean shouldSwap = numbers[lastProcessedIndex] > numbers[lastProcessedIndex + 1];
    
        if (shouldSwap) {
            int temp = numbers[lastProcessedIndex];
            numbers[lastProcessedIndex] = numbers[lastProcessedIndex + 1];
            numbers[lastProcessedIndex + 1] = temp;
    
            lastProcessedIndex++;
            displayArrayWithHighlight();
            swapCount++;
            if (Arrays.equals(numbers, tempArray)) {
                penaltyTime += PENALTY;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Numbers " + numbers[lastProcessedIndex] + " and " + numbers[lastProcessedIndex + 1] + " should not be swapped.",
                    "Invalid Swap", JOptionPane.WARNING_MESSAGE);
            penaltyTime += 5000;
        }
        endOfPass();
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
            lastProcessedIndex = 0;
            endOfPass();
        } else {
            if (isSorted()) {
                long endTime = System.currentTimeMillis();
                double timeTaken = (endTime - startTime) / 1000;
                int option = JOptionPane.showOptionDialog(mainPanel,
                        "Congratulations! You sorted the array in " + timeTaken + " seconds with " + swapCount
                                + " swaps. An additional " + penaltyTime/1000 + " seconds was added for every wrong decision. Total time is " + (timeTaken + (penaltyTime / 1000)) + " seconds.",
                        "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[] { "Play Again", "Quit" }, "Play Again");
    
                if (option == 0) {
                    initializeGame();
                    displayArrayWithHighlight();
                    startTime = System.currentTimeMillis();
                    swapCount = 0;
                    penaltyTime = 0;
                    lastProcessedIndex = 0;
                    updateTimer();
                    endOfPass();
                } else {
                    System.exit(0);
                }
            } else {
                    int option = JOptionPane.showOptionDialog(mainPanel, "Sorry, you lose. The array is not sorted.",
                            "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
                            new String[] { "Try Again", "Quit" }, "Try Again");
    
                    if (option == 0) {
                        initializeGame();
                        displayArrayWithHighlight();
                        startTime = System.currentTimeMillis();
                        swapCount = 0;
                        penaltyTime = 0;
                        lastProcessedIndex = 0;
                        updateTimer();
                        endOfPass();
                    } else {
                        System.exit(0);
                    }
            }
        }
    }

    private void showRandomHint() {
        if (hintOptionPane != null && hintOptionPane.isVisible()) {
            hintOptionPane.setVisible(false);

            if (getExtendedState() == ICONIFIED || gameEnded) {
                return;
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

        UIManager.put("OptionPane.background", new Color(255, 240, 245));
        UIManager.put("Panel.background", new Color(255, 240, 245));

        
        JOptionPane.showMessageDialog(this, hints[index], "Hint", JOptionPane.INFORMATION_MESSAGE);

        
        UIManager.put("OptionPane.background", UIManager.get("OptionPane.background"));
        UIManager.put("Panel.background", UIManager.get("Panel.background"));
    }

    class CircleShape extends JPanel {
        private Color color = Color.PINK;
        private String text = "";

        CircleShape() {
            setPreferredSize(new Dimension(100, 100)); 
        }

        void setColor(Color color) {
            this.color = color;
            repaint(); 
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
            int circleSize = 100; 
            int x = (getWidth() - circleSize) / 2;
            int y = (getHeight() - circleSize) / 2;
            g.fillOval(x, y, circleSize, circleSize);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, circleSize, circleSize);
            Font font = g.getFont().deriveFont(Font.BOLD, 16); 
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            g.drawString(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4);
        }
    }
}