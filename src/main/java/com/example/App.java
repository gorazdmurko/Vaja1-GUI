package com.example;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;





/*
 * Hello world!
 *
 */
public class App {

    private static File selectedFile;
    private static JTextField txtField;
    private static JButton drawButton;
    private static JLabel fileLabel;
    private static JPanel btnPanel;
    private static GraphPanel graphPanel;

    public App() {
        selectedFile = null;
    }

    // MAIN METHOD
    public static void main( String[] args ) {
        run();
    }

    private static void run() {
        JFrame mainWin = new JFrame("Unit 29 - Vaja1 1");
        mainWin.setSize(800, 600);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. panel with label, textField, button & button
        btnPanel = new JPanel();
        fileLabel = new JLabel("datoteka");
        txtField = new JTextField(36);
        txtField.setEditable(false);
        final JButton openButton = new JButton("Izberi Datoteko"); // you can not inherit from final class
        drawButton = new JButton("Narisi Graf");
        drawButton.setEnabled(false);
        final JButton resetButton = new JButton("Reset");
        resetButton.setEnabled(false);
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(fileLabel); btnPanel.add(txtField);                                // add label textField to Panel
        btnPanel.add(openButton); btnPanel.add(drawButton); btnPanel.add(resetButton);  // add all 3 buttons to Panel

        // 2. panel with graph - from upper Class !!
        graphPanel = new GraphPanel();
        mainWin.getContentPane().add(BorderLayout.CENTER, graphPanel);  // graphPanel to JFrame
        mainWin.getContentPane().add(BorderLayout.SOUTH, btnPanel);     // btnPanel to JFrame

        // action listeners
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                txtField.setText(selectedFile.getName());
                drawButton.setEnabled(true);
            }
        });

        drawButton.addActionListener(e -> {
            java.util.List<Float> listTemperatures = new ArrayList<Float>();        // use list, because it is dynamic
            try {
                Scanner reader = new Scanner(selectedFile);
                while(reader.hasNextLine()) {
                    listTemperatures.add(Float.parseFloat(reader.nextLine()));
                }
                reader.close();
                graphPanel.setTemperatures(listTemperatures.toArray(new Float[0]));  // setTemperatures changes the value of tempsArray !!
                // graphPanel.repaint();
                resetButton.setEnabled(true);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        resetButton.addActionListener(e -> {
            java.util.List<Float> list = new ArrayList<Float>();
            graphPanel.setTemperatures(list.toArray(new Float[0]));
            // graphPanel.repaint();
            System.out.println("Reset complete");
            resetButton.setEnabled(false);
        });

        mainWin.setVisible(true);
    }
}