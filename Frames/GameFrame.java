package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class GameFrame extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 20);
    JLabel label = new JLabel("Welcome to the Bubble Sort Game!");
    JLabel instructionLabel = new JLabel("Instructions: Should the highlighted numbers be swapped?");  
    JLabel timeLabel = new JLabel("Time: 0 seconds");    

    // For main panel
    JPanel mainPanel = new JPanel(new GridLayout(5, 1)); // GridLayout with 4 rows and 1 column

    JButton yesButton;
    JButton noButton;

    private static final int ARRAY_SIZE = 10;
    private static final int MAX_NUMBER = 100;
    private static final int PENALTY = 5;

    private int[] numbers;
    private int penaltyTime = 0;
    private long startTime;

    CircleShape[] numberShapes = new CircleShape[ARRAY_SIZE];

    private int lastProcessedIndex = 0; // Initialize the last processed index
    private int swapCount = 0;

    GameFrame() {
        // Main panel settings
       // Create the main panel
JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, -5)); // GridLayout with 3 rows, 1 column, and 10px horizontal and vertical gap
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
JPanel circlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // FlowLayout with center alignment and 10px horizontal and vertical gap
circlePanel.setBackground(new Color(255, 205, 234));

for (int i = 0; i < ARRAY_SIZE; i++) {
    numberShapes[i] = new CircleShape();
    circlePanel.add(numberShapes[i]);
}

mainPanel.add(circlePanel);

// Add a panel for buttons to main panel
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50)); // FlowLayout with center alignment and 10px horizontal and vertical gap
buttonPanel.setBackground(new Color(255, 205, 234));

// Add yes and no buttons
yesButton = new JButton("Yes");
yesButton.setPreferredSize(new Dimension(150, 60)); // Set preferred size for Yes button
noButton = new JButton("No");
noButton.setPreferredSize(new Dimension(150, 60));

buttonPanel.add(yesButton);
buttonPanel.add(noButton);

mainPanel.add(buttonPanel);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapNumbers();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastProcessedIndex++; // Move one index forward
                displayArrayWithHighlight(); // Update the display

                // Check if it's the last pass, prompt for another pass
                if (lastProcessedIndex >= ARRAY_SIZE - 1) {
                    promptForNextPass();
                } else {
                    bubbleSort(); // Continue sorting after penalty
                }
            }
        });

       

        // Add main panel to the frame
        add(mainPanel);

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
        new Timer(1000, new ActionListener() {
            int secondsElapsed = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                timeLabel.setText("Time: " + secondsElapsed + " seconds");
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle other actions if needed
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
        int response = JOptionPane.showConfirmDialog(this, "Do you want to perform another pass?", "Next Pass", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            lastProcessedIndex = 0; // Reset the lastProcessedIndex
            bubbleSort(); // Perform another pass
        } else {
            // Check if the array is sorted after the last pass
            
        if (isSorted()) {
            long endTime = System.currentTimeMillis();
            double timeTaken = (endTime - startTime + penaltyTime)/1000;
            JOptionPane.showMessageDialog(this, "Congratulations! You sorted the array in " + timeTaken + " seconds with " + swapCount + " swaps.");
        } else {
                JOptionPane.showMessageDialog(this, "Sorry, you lose. The array is not sorted.");
            }
        }
    }

    private int[] getHighlightedIndices() {
        int[] indices = new int[2];
        indices[0] = lastProcessedIndex;
        indices[1] = lastProcessedIndex + 1;

        return indices;
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