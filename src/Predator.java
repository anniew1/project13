import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Predator {

    private ArrayList<BufferedImage> frames;
    int currentFrame;

    public Predator(ArrayList<BufferedImage> frames) {
        this.frames = frames;
        currentFrame = 0;
    }
}
