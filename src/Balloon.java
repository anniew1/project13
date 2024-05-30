import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Balloon extends Animation {

    double x;
    double y;
    int turnNum;
    Boolean right;
    Boolean once;

    public Balloon(ArrayList<BufferedImage> frames, int delay) {
        super(frames, delay);
        x = 209;
        y = 5;
        turnNum = 0;
        right = true;
        once = true;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public Boolean getOnce() {
        return once;
    }

    public void setOnce(Boolean value) {
        once = value;
    }

    public void move() {
        if (turnNum % 2 == 0) {
            incrementY();
        } else {
            incrementX();
        }
    }

    private void incrementX() {
        if (turnNum < 3 || turnNum == 9) {
            x += .5;
        } else {
            x -= .5;
        }
    }

    private void incrementY() {
        y += .5;
    }

    public void incrementTurnNum() {
        turnNum++;
    }

    public Rectangle getRect() {
        int imageHeight = getActiveFrame().getHeight();
        int imageWidth = getActiveFrame().getWidth();
        return new Rectangle((int) x, (int) y, imageWidth, imageHeight);
    }
}









