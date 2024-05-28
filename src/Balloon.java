import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Balloon extends Animation {

    double x;
    double y;
    int turnNum;
    Boolean right;

    public Balloon(ArrayList<BufferedImage> frames, int delay) {
        super(frames, delay);
        x = 300;
        y = 0;
        turnNum = 0;
        right = true;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void move() {
        checkLocation();
        if (turnNum == 0) {
            incrementY();
        } else if (turnNum == 1) {
            incrementX();
        } else if (turnNum == 2) {
            incrementY();
        } else if (turnNum == 3) {
            incrementX();
        } else if (turnNum == 4) {
            incrementY();
        } else if (turnNum == 5) {
            incrementX();
        } else if (turnNum == 6) {
            incrementY();
        } else if (turnNum == 7) {
            incrementX();
        } else if (turnNum == 8) {
            incrementY();
        } else if (turnNum == 9) {
            incrementX();
        } else if (turnNum == 10) {
            incrementY();
        }
    }


    private void incrementX() {
        if (right) {
            x += 0.1;
        } else {
            x -= 0.1;
        }
    }

    private void incrementY() {
        y += 0.1;
    }

    private void checkLocation() {

    }
}
