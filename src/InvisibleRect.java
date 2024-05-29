import java.awt.*;

public class InvisibleRect {

    int x;
    int y;

    Rectangle rect;

    public InvisibleRect(int x, int y) {
        rect = new Rectangle(20, 20);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRect() {
        return rect;
    }
}
