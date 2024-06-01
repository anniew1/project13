import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Predator extends Animation {

    private ArrayList<BufferedImage> frames;
    int currentFrame;
    int x;
    int y;

    public Predator(ArrayList<BufferedImage> frames, int x, int y) {
        super(frames, 1);
        this.x = x;
        this.y = y;
        currentFrame = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
