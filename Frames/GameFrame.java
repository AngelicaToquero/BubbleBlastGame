package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    JLabel label = new JLabel("Welcome to the Bubble Sort Game!");

    // For main panel
    JPanel mainPanel = new JPanel(null);
    
    GameFrame() {
        // Main panel settings
        mainPanel.setBackground(new Color(255, 205, 234));
        mainPanel.setBounds(0, 0, 1100, 700);

        // Frame settings
        add(label);
        setTitle("Bubble Sort Game");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        ImageIcon icon = new ImageIcon("Frames\\pictures\\photo_2024-03-30_23-39-13.jpg");
        setIconImage(icon.getImage());
        add(mainPanel);
    }
}
