package Frames;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LandingFrame frame= new LandingFrame();
            frame.setVisible(true);
        });
    }
}