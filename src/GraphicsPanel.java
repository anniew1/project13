import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    private BufferedImage background;
    private BufferedImage pig;

    private boolean[] pressedKeys;
    private Timer timer;
    private int time;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/dwasdwa.png"));
            pig = ImageIO.read(new File ("src/predator1.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pressedKeys = new boolean[128];
        time = 0;
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);  // the order that things get "painted" matter; we put background down first
        g.drawImage(pig, 100, 100, null);
            }

}


