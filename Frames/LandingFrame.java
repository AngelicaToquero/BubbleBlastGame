package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingFrame extends JFrame implements ActionListener {
    GradientButton button = new GradientButton("Start Game");
    ImageIcon backgroundImage = new ImageIcon("Frames\\Bckgrd.JPG");
    ImageIcon image = new ImageIcon("Frames\\pictures\\photo_2024-03-31_00-00-54-removebg-preview.png");

    LandingFrame() {
        
        setTitle("BubbleSort Blast Game");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        ImageIcon icon = new ImageIcon("Frames\\pictures\\photo_2024-03-30_23-39-13.jpg");
        setIconImage(icon.getImage());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1100, 700));
        setContentPane(layeredPane);

        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, -10, 1100, 700);
        layeredPane.add(backgroundLabel, BorderLayout.CENTER);

        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, 1100, 300);
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

        JLabel titleLabel = new JLabel("<html><center>BUBBLESORT <br>BLAST GAME</center></html>");
        titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 100)); 
        titleLabel.setForeground(Color.black); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        titleLabel.setBounds(0, 150, 1100, 300); 
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        button.setBounds(475, 450, 150, 50); 
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            HomeFrame home = new HomeFrame();
            home.setVisible(true);
            home.setLocationRelativeTo(null);
            this.dispose();
        }
    }
}

class GradientButton extends JButton {
    public GradientButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setOpaque(true);
    }
}
