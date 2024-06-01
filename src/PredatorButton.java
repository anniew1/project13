import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PredatorButton {

    private int x;
    private int y;

    private BufferedImage image;

    public PredatorButton(int x, int y, int imageNum) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("src/assets/predator" + imageNum + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() {
        return image;
    }
}
