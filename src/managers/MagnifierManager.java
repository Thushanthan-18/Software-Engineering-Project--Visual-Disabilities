package managers;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * The manager Class responsible for creating new magnifier instance(object).
 * configuration and interface(mouse) logic is specified to get concurrent thread, for magnification.
 */

public class MagnifierManager extends JFrame implements Runnable {
    private Robot robot;
    private JLabel magnifiedView;
    public MagnifierManager() {
        super("Magnifying Tool");
        setSize(500, 500);//set size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        magnifiedView = new JLabel();
        add(magnifiedView);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (isVisible()) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            Rectangle captureRect = new Rectangle(mouseLocation.x - 30, mouseLocation.y - 100, 200, 200);
            BufferedImage capture = robot.createScreenCapture(captureRect);
            ImageIcon icon = new ImageIcon(capture.getScaledInstance(400, 400, Image.SCALE_DEFAULT));
            magnifiedView.setIcon(icon);
            try {
                Thread.sleep(10); // Adjust refresh rate as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void execute() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of MagnifyingTool
                MagnifierManager magnifyingTool = new MagnifierManager();

                // Make it visible
                magnifyingTool.setVisible(true);

                // Start the magnify thread
                Thread magnifyThread = new Thread(magnifyingTool);
                magnifyThread.start();
            }
        });
    }
}