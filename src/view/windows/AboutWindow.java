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
 * @author Chris Olive, Dean Ashton
 */

package view.windows;

import managers.WindowManager;
import utils.LinkListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;


public class AboutWindow {
    private final JPanel jpMain = new JPanel();
    private final JPanel jPanel1 = new JPanel();
    private final JLabel jlHeat = new JLabel();
    private final FlowLayout flowLayout1 = new FlowLayout();
    private final JLabel jLabel1 = new JLabel();
    private JDialog dialog;
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JPanel jPanel2 = new JPanel();
    private final JButton jbClose = new JButton();
    private final FlowLayout flowLayout2 = new FlowLayout();
    private final JEditorPane jEditorPane1 = new JEditorPane();

    private static final Logger log = Logger.getLogger("heat");
    private static final File localFile = new File("html/about.html");
    private java.net.URL htmURL;
    private final String indexFile = "html/about.html";

    public AboutWindow() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jpMain.setLayout(borderLayout1);
        jPanel1.setLayout(flowLayout1);
        jlHeat.setText("HEAT");
        jlHeat.setFont(new Font("Dialog", 1, 16));
        flowLayout1.setAlignment(0);
        jLabel1.setText("Version 5.04");
        jPanel2.setLayout(flowLayout2);
        jbClose.setText("Close");
        jbClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jbClose_actionPerformed(e);
            }
        });
        jEditorPane1.setText("jEditorPane1");
        jEditorPane1.setEditable(false);
        try {
            htmURL = this.getClass().getClassLoader().getResource(indexFile);

            if (htmURL == null)
                log.warning("[TutorialWindow] - Resource not found:" + localFile);
            else
                jEditorPane1.setPage(htmURL);
        } catch (Exception e) {
            jEditorPane1.setText("Error: Could not find html page: " +
                    localFile.getAbsolutePath());
        }
        jEditorPane1.addHyperlinkListener(new LinkListener(jEditorPane1));
        jPanel1.add(jlHeat, null);
        jPanel1.add(jLabel1, null);
        jpMain.add(jPanel1, BorderLayout.NORTH);
        jPanel2.add(jbClose, null);
        jpMain.add(jPanel2, BorderLayout.SOUTH);
        jpMain.add(jEditorPane1, BorderLayout.CENTER);
    }

    public void show() {
        dialog = new JDialog(WindowManager.getInstance().getMainScreenFrame(),
                "About HEAT");
        dialog.setModal(true);
        dialog.getContentPane().add(jpMain);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(WindowManager.getInstance().getMainScreenFrame());
        dialog.setVisible(true);
    }

    private void jbClose_actionPerformed(ActionEvent e) {
        dialog.dispose();
    }
}
