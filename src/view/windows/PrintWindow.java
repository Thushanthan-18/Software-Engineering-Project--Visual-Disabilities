/**
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 * <p>
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 * @author Louis Whest
 */

package view.windows;

import managers.WindowManager;
import utils.Printer;
import utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PrintWindow {
    private final WindowManager wm = WindowManager.getInstance();
    private final JPanel jPanel0 = new JPanel();
    private final JPanel jPanel1 = new JPanel();
    private final JPanel jPanel2 = new JPanel();
    private final JPanel jpMessage = new JPanel();
    private Icon iconCode = new ImageIcon();
    private Icon iconOutput = new ImageIcon();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JButton jCode = new JButton();
    private final JButton jOutput = new JButton();
    private final JLabel jlDialogMessage = new JLabel();
    JFrame frame = new JFrame("Print");
    Printer printer = new Printer();

    public PrintWindow() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        iconCode = Resources.getIcon("printcode16");
        iconOutput = Resources.getIcon("printoutput16");

        jPanel0.setSize(new Dimension(300, 100));

        jPanel1.setLayout(borderLayout1);

        jCode.setText("Code");
        jCode.setPreferredSize(new Dimension(130, 25));
        jCode.setIcon(iconCode);
        jCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jCode_actionPerformed(e);
            }
        });

        jOutput.setText("Output");
        jOutput.setPreferredSize(new Dimension(130, 25));
        jOutput.setIcon(iconOutput);
        jOutput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jOutput_actionPerformed(e);
            }
        });

        jlDialogMessage.setText("Please select which area you would like to print.");
        jpMessage.setSize(new Dimension(150, 21));
        jpMessage.add(jlDialogMessage, null);

        jPanel2.add(jCode, null);
        jPanel2.add(jOutput, null);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);

        jPanel1.add(jpMessage, BorderLayout.NORTH);
        jPanel0.add(jPanel1, null);
    }

    public void show() {
        frame.getContentPane().add(jPanel0);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(wm.getMainScreenFrame());
        frame.setVisible(true);
    }

    private void jCode_actionPerformed(ActionEvent e) {
        close();
        printCode();
    }

    private void jOutput_actionPerformed(ActionEvent e) {
        close();
        printOutput();
    }

    public void printCode() {
        printer.print(EditorWindow.getTextPane());
    }

    public void printOutput() {
        printer.printOut(wm.getConsoleWindow().getTextArea());
    }

    public void close() {
        frame.dispose();
    }
}
