package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame implements ActionListener {
    JLabel label = new JLabel();

    // For navigation panel
    JPanel navigationPanel = new JPanel(null);

    JButton playButton = new JButton("PLAY GAME");
    JLabel imageLabel = new JLabel(new ImageIcon("Frames\\Logo.JPG")); // Set your image path

    // For menu bar
    JPanel menuBar = new JPanel(null);

    // For main panel
    JPanel mainPanel = new JPanel(null);
    private boolean modified = false;

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    // Method to check if any changes have been made
    public boolean isModified() {
        return modified;
    }

    HomeFrame() {
        // Panel on left side
        GradientPaint gradient = new GradientPaint(0, 0, new Color(100, 32, 170), getWidth(), getHeight(),
                new Color(255, 69, 0));
        navigationPanel.setOpaque(false);
        navigationPanel.setBounds(0, 0, 275, 210);

        // Add image label
        imageLabel.setBounds(10, 6, 250, 200); // Adjust coordinates and size as needed
        navigationPanel.add(imageLabel);

        playButton.setBounds(850, 500, 150, 60);
        playButton.addActionListener(this);
        playButton.setFont(new Font("Arial", Font.BOLD, 16));
        playButton.setBorder(BorderFactory.createRaisedBevelBorder());

        mainPanel.add(playButton);

        // Add text to the menu bar panel
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>BUBBLESORT BLAST<br>GAME</div></html>");
        titleLabel.setForeground(Color.BLACK); // Set text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 70)); // Set font and size
        titleLabel.setBounds(300, 10, 1000, 200); // Set position and size
        menuBar.add(titleLabel); // Add to menu bar panel

        menuBar.setBackground(new Color(198, 91, 207)); // Set background color
        menuBar.setBounds(0, 0, 1100, 210); // Set position and size

        // Main panel settings
        mainPanel.setBackground(new Color(255, 205, 234));
        mainPanel.setBounds(0, 60, 1100, 660);

        // Create a JLabel with the desired text
        // Create a JLabel with the desired text
        JLabel mainTextLabel = new JLabel("<html><font size='10' color='#000000'><b>Dear Player,</b></font><br><br>" +
                "<font size='8' color='#000000'><b>Objective:</b><br>" +
                "&emsp;Sort the given array using bubble sort.<br><br>" +
                "<b>Instructions:</b><br>" +
                "&emsp;Observe the array and swap adjacent numbers to sort it. Time yourself to complete the sorting task.<br><br>"
                +
                "<left><font size='14' color='#FF0000'>Ready to dive into the bubble sort challenge?</left></html>");

        mainTextLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font and size of the text
        mainTextLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align the text to the center
        mainTextLabel.setBounds(50, 100, 1000, 500); // Set the position and size of the label

        // Add the JLabel to the main panel
        mainPanel.add(mainTextLabel);

        // Frame settings
        add(label);
        setTitle("BubbleSort Blast Game");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        ImageIcon icon = new ImageIcon("Frames\\pictures\\photo_2024-03-30_23-39-13.jpg");
        setIconImage(icon.getImage());
        add(navigationPanel);
        add(menuBar);
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            openGamePage();
        }
    }

    private void openGamePage() {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);
        dispose(); // Close the current frame
    }
}