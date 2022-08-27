package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;



public class GraphPanel extends JPanel {

    private Float[] tempsArray;
    // constructor
    public GraphPanel() {

        tempsArray = null;
    }

    // our method
    private void doDrawing(Graphics g) {    // draws coordinate system
        if(tempsArray != null) {
            System.out.println("PAINTING - AGAIN");
        } else {
            System.out.println("PAINTING");
        }
        Graphics2D g2d = (Graphics2D) g;
        setBackground(Color.BLACK);
        int xres = this.getWidth();      // width / height of panel (which is added into frame)
        int yres = this.getHeight();
        // System.out.println("X: " + xres);
        // System.out.println("Y: " + yres);
        g2d.setColor(Color.GREEN);   // color of co.sys. x,y
        int xstart = 30; int ystart = yres - 30;                // starting X & Y points of coordinate system
        int xend = xres - 20; int yend = 20;                    // ending X & Y points of coordinate system
        int xsize = xend - xstart;          // X size of co.sys.
        int ysize = ystart - yend;          // y size of co.sys.
        int dx100 = (100 * xsize)/24;  // dx
        int dy100 = (100 * ysize)/60;  // dy

        // draw grid
        g2d.drawLine(xstart, ystart, xstart, yend);  // y-line of co.sys.
        g2d.drawLine(xstart, ystart, xend, ystart);  // x-line of co.sys

        // x
        for (int i = 0; i < 24; i++) {
            int x = xstart + (dx100*i)/100 + dx100/200;
            g2d.drawLine(x, ystart, x, ystart + 4);  // y koordinata je obrnjena navzdol
            String hrStr = Integer.toString(i + 1);
            Rectangle2D rect = g2d.getFontMetrics().getStringBounds(hrStr, g2d);
            g2d.drawString(hrStr, (float)(x - rect.getCenterX()), (float)(ystart + 4 + rect.getHeight()));
        }
        // y
        for (int i = 4; i < 60; i+=4) {
            int y = ystart - (dy100*i)/100;
            g2d.drawLine(xstart - 4, y, xstart, y);
            String tmpStr = Integer.toString(20 + i);
            Rectangle2D rect = g2d.getFontMetrics().getStringBounds(tmpStr, g2d);
            g2d.drawString(tmpStr, (float)(24 - rect.getWidth()), (float)(y - rect.getCenterY()));
        }
        // when 'tempsArray' array is no longer null, it draws graph
        if (tempsArray != null) {
            System.out.println("PAINTING - GRAPH");
            // draw temperature bars
            int yzero = ystart - (dy100+20)/100;
            g2d.setColor(Color.RED);
            g2d.drawLine(xstart + 1, yzero, xend, yzero);
            for (int i = 0; i < tempsArray.length && i < 24; i++) {
                float temp = (float) tempsArray[i];
                if (temp > 40.0f) { temp = 40.0f; }
                if (temp < -20.0f) { temp = -20.0f; }
                int x = xstart + (dx100 * i)/100;
                if (temp > 0) {
                    int height = (int)((dy100 * temp)/100 - 1);
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect(x + 5, yzero - height - 1, dx100/100 - 10, height);
                } else if (temp < 0) {
                    int height = (int)((dy100*(-temp))/100);
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(x + 5, yzero + 1, dx100/100 - 10, height);
                }
            }
        }
    }

    // ----------------------------------------------------------------
    protected void paintComponent(Graphics g) {     // this method already exists in JPanel class   -> it needs to call super w/Graphics object as parameter
        super.paintComponent(g);
        doDrawing(g);
    }
    // ----------------------------------------------------------------
    public void setTemperatures(Float[] tempsArray) {
        this.tempsArray = tempsArray;
        System.out.println("\n\n");
        repaint();
        // updateUI();;
    }
}