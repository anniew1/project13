import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Balloon extends Animation implements ActionListener {

    double x;
    double y;
    int turnNum;
    private Timer timer;
    private double time;
    Boolean leftSide;

    public Balloon(ArrayList<BufferedImage> frames, int delay, Boolean leftSide) {
        super(frames, delay);
        this.leftSide = leftSide;
        if (leftSide) {
            x = 209;
        } else {
            x = 960;
        }
        y = 5;
        turnNum = 0;
        time = 0;
        timer = new Timer(1000, this);
       // timer.start();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public double getTime() {
        return time;
    }

    public void zeroTime() {
        time = 0;
    }

    public boolean move() {
        if (turnNum % 2 == 0) {
            incrementY();
        } else {
            incrementX();
        }
        return checkOutside();
    }

    private void incrementX() {
        double moveValue;
        if (leftSide) {
            moveValue = .6;
        } else {
            moveValue = -.6;
        }
        if ((turnNum < 3 || turnNum == 9)) {
            x += moveValue;
        } else {
            x -= moveValue;
        }
    }

    private void incrementY() {
        y += .6;
    }

    public void incrementTurnNum() {
        turnNum++;
    }

    public Rectangle getRect() {
        int imageHeight = getActiveFrame().getHeight();
        int imageWidth = getActiveFrame().getWidth();
        return new Rectangle((int) x, (int) y, imageWidth, imageHeight);
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time += .01;
        }
    }

    private boolean checkOutside() {
        if (y >= 600) {
            return true;
        }
        return false;
    }

}









