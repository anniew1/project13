import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bullet {

    int x;
    int y;
    BufferedImage image;


    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("src/assets/bullet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setX(int z) {
        x = z;
    }

    public void setY(int z) {
        y = z;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {return (int) y;}

    public Rectangle getRect() {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        Rectangle rect = new Rectangle((int) x, (int) y, imageWidth, imageHeight);
        return rect;

    }
}
