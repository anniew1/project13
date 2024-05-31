import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Predator {

    private ArrayList<BufferedImage> frames;
    int currentFrame;
    int x;
    int y;

    public Predator(ArrayList<BufferedImage> frames, int x, int y) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        currentFrame = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
