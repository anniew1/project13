import java.awt.*;

public class InvisibleRect {

    int x;
    int y;

    public InvisibleRect(int x, int y) {
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
        return new Rectangle(x, y, 20, 20);
    }
}
