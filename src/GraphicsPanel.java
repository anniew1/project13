import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    private Animation animate;
    private BufferedImage background;
    private BufferedImage pig;
    private boolean[] pressedKeys;
    private BufferedImage cat1;
    private BufferedImage cat2;
    private Timer timer;
    private int time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> catFrames;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/assets/dwasdwa.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pigFrames = new ArrayList<>();
        catFrames = new ArrayList<>();
        importImages(1, 10, pigFrames);
        importImages(11, 12, catFrames);
        pressedKeys = new boolean[128];
        time = 0;
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
        animate = new Animation(catFrames, 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);  // the order that things get "painted" matter; we put background down first
        g.drawImage(pig, 100, 100, null);
        g.drawImage(animate.getActiveFrame(), 200, 100, null);
    }

    private void importImages(int startNum, int endNum, ArrayList<BufferedImage> list) {
        for (int i = startNum; i <= endNum; i++) {
            try {
                list.add(ImageIO.read(new File("src/assets/predator" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}


