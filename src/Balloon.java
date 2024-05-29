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
        if (turnNum % 2 == 0) {
            incrementY();
        } else {
            incrementX();
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









