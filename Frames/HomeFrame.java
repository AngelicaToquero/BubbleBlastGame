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
    JButton quitButton = new JButton("QUIT GAME");
    JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\PC\\Desktop\\Logo.JPG")); // Set your image path
    JLabel textLabel = new JLabel("<html><font size='6' color='#FFFFFF'><b>Dear Player,</b></font><br><br>" +
        "<font size='4' color='#FFFFFF'><b>Objective:</b><br>" +
        "&emsp;Sort the given array using bubble sort.<br><br>" +
        "<b>Instructions:</b><br>" +
        "&emsp;Observe the array and swap adjacent numbers to sort it. Time yourself to complete the sorting task.<br><br>" +
        "<center>Ready to test your grasp of bubble sort?</font></html>");

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
        //Panel on left side
        navigationPanel.setBackground(new Color(100, 32, 170)); // Violet color
        navigationPanel.setBounds(0, 0, 275, 700);
        
        // Add image label
        imageLabel.setBounds(10, 8, 250, 200); // Adjust coordinates and size as needed
        navigationPanel.add(imageLabel);
        
        // Add text label
        textLabel.setBounds(5, 200, 260, 300); // Adjust coordinates and size as needed
        navigationPanel.add(textLabel);
        
        playButton.setBounds(50, 500, 150, 60);
        playButton.addActionListener(this);
        playButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        navigationPanel.add(playButton);

        quitButton.setBounds(50, 580, 150, 60);
        quitButton.addActionListener(this);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        navigationPanel.add(quitButton);

        // Add text to the menu bar panel
        JLabel titleLabel = new JLabel("BUBBLESORT BLAST GAME");
        titleLabel.setForeground(Color.BLACK); // Set text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Set font and size
        titleLabel.setBounds(330, 30, 1000, 100); // Set position and size
        menuBar.add(titleLabel); // Add to menu bar panel

        menuBar.setBackground(new Color(198, 91, 207)); // Set background color
        menuBar.setBounds(0, 0, 1100, 150); // Set position and size

        // Main panel settings
        mainPanel.setBackground(new Color(255, 205, 234));
        mainPanel.setBounds(0, 60, 1100, 660);

        // Frame settings
        add(label);
        setTitle("BubbleBlastGame");
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
        if (e.getSource() == quitButton) {
            LandingFrame landingFrame = new LandingFrame();
            landingFrame.setVisible(true);
            landingFrame.setLocationRelativeTo(null);
            dispose(); // Close the current frame
        } else if (e.getSource() == playButton) {
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
