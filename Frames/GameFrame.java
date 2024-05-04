package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;


public class GameFrame extends JFrame implements ActionListener {
    JLabel label = new JLabel("Welcome to the Bubble Sort Game!");
    JLabel instructionLabel = new JLabel("Instructions: Should the highlighted numbers be swapped?");
    JLabel timeLabel = new JLabel("Time: 0 seconds");

    // For main panel
    JPanel mainPanel = new JPanel(new GridLayout(4, 3));

    private static final int ARRAY_SIZE = 10;
    private static final int MAX_NUMBER = 100;
    private static final int PENALTY = 2;

    private int[] numbers;
    private int penaltyTime = 0;
    private int swapsCount = 0;
    private long startTime;

    CircleButton[] numberButtons = new CircleButton[ARRAY_SIZE];

    GameFrame() {
        // Main panel settings
        mainPanel.setBackground(new Color(255, 205, 234));

        label.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to main panel
        mainPanel.add(label);
        mainPanel.add(instructionLabel);
        mainPanel.add(timeLabel);

        for (int i = 0; i < ARRAY_SIZE; i++) {
            numberButtons[i] = new CircleButton();
            numberButtons[i].addActionListener(this);
            mainPanel.add(numberButtons[i]);
        }

        // Add main panel to the frame
        add(mainPanel);

        // Frame settings
        setTitle("Bubble Sort Game");
        setSize(800, 600);
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
            numberButtons[i].setText(Integer.toString(numbers[i]));
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

    private void checkGameStatus() {
        boolean sorted = true;
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                sorted = false;
                break;
            }
        }

        if (sorted) {
            long endTime = System.currentTimeMillis();
            double timeTaken = (endTime - startTime - penaltyTime) / 1000.0;
            JOptionPane.showMessageDialog(this, "Congratulations! You sorted the array in " + timeTaken + " seconds with " + swapsCount + " swaps.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (i < ARRAY_SIZE - 1 && shouldSwap(numbers[i], numbers[i + 1])) {
                    String message = "Should the highlighted numbers be swapped? (yes/no)";
                    int option = JOptionPane.showConfirmDialog(this, message, "Swap Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        swap(numbers, i, i + 1);
                        swapsCount++;
                        penaltyTime += PENALTY * 1000;
                        displayNumbers();
                        checkGameStatus();
                    }
                }
            }
        }
    }

    private boolean shouldSwap(int a, int b) {
        return a > b; // Change condition based on whether you want ascending or descending order
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    // Custom JButton class for circular buttons
    class CircleButton extends JButton {
        CircleButton() {
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }

        @Override
        Shape getShape() {
            return new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 40);
        }
    }
}
