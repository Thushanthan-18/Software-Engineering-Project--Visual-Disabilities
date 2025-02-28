package view.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeatureSelectionScreen extends JFrame {
    private int mode = 0;

    public int getMode() {
        return mode;
    }

    public FeatureSelectionScreen() {
        setTitle("HEAT IDE - Accessibility Features");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 20, 20)); // Two sections for buttons

        // Button properties
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        Color buttonTextColor = Color.BLACK;
        Color buttonBackgroundColor = new Color(50, 50, 50); // Dark Gray

        // Load images (Replace with actual paths)
        ImageIcon logo1 = new ImageIcon("path_to_logo1.png");
        ImageIcon logo2 = new ImageIcon("path_to_logo2.png");

        // ---- Normal Version ----
        JPanel normalPanel = new JPanel();
        normalPanel.setLayout(new BoxLayout(normalPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        normalPanel.setBackground(Color.WHITE);

        JLabel normalImages = new JLabel(logo1);
        normalImages.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton normalButton = new JButton("Default Mode");
        normalButton.setFont(buttonFont);
        normalButton.setForeground(buttonTextColor);
        normalButton.setBackground(buttonBackgroundColor);
        normalButton.setFocusPainted(false);
        normalButton.setToolTipText("Launch the normal version of HEAT IDE");
        normalButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        normalPanel.add(Box.createVerticalGlue());
        normalPanel.add(normalImages);
        normalPanel.add(Box.createVerticalStrut(10));
        normalPanel.add(normalButton);
        normalPanel.add(Box.createVerticalGlue());

        // ---- Visual Impaired Mode ----
        JPanel impairedPanel = new JPanel();
        impairedPanel.setLayout(new BoxLayout(impairedPanel, BoxLayout.Y_AXIS));
        impairedPanel.setBackground(Color.BLACK);

        JLabel impairedImages = new JLabel(logo2);
        impairedImages.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton impairedButton = new JButton("Accessibility Mode");
        impairedButton.setFont(buttonFont);
        impairedButton.setForeground(buttonTextColor);
        impairedButton.setBackground(buttonBackgroundColor);
        impairedButton.setFocusPainted(false);
        impairedButton.setToolTipText("Launch the visually impaired mode of HEAT IDE");
        impairedButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        impairedPanel.add(Box.createVerticalGlue());
        impairedPanel.add(impairedImages);
        impairedPanel.add(Box.createVerticalStrut(10));
        impairedPanel.add(impairedButton);
        impairedPanel.add(Box.createVerticalGlue());

        // Add panels to frame
        add(normalPanel);
        add(impairedPanel);

        // Add action listeners
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 0;
                dispose();
                //launchNormalMode(); // not needed
            }
        });

        impairedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 1;
                dispose();
                //launchImpairedMode(); // not needed
            }
        });
    }

    /**
     * Launches Normal Mode (OG IDE) using NormalModeLauncher
     */
    private void launchNormalMode() {
        try {
            System.out.println("Launching Normal Mode...");
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "src.NormalModeLauncher");
            pb.start();
            dispose(); // Close the feature selection screen
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to launch Normal Mode", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Launches Visual Impaired Mode (Existing mode)
     */
    private void launchImpairedMode() {
        try {
            System.out.println("Launching Visual Impaired Mode...");
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "src.Main");
            pb.start();
            dispose(); // Close the feature selection screen
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to launch Visual Impaired Mode", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeatureSelectionScreen screen = new FeatureSelectionScreen();
            screen.setVisible(true);
        });
    }

 */
}