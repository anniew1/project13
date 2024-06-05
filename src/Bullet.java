import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bullet {

    int x;
    int y;


    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
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
}
