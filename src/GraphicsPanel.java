import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements ActionListener implements MouseListener {
    private Animation animateCat;
    private Animation animatePig;
    private Animation animatePig2;
    private BufferedImage background;
    private boolean[] pressedKeys;
    private Timer timer;
    private int time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> catFrames;
    private ArrayList<BufferedImage> pig2Frames;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/assets/dwasdwa.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pigFrames = new ArrayList<>();
        catFrames = new ArrayList<>();
        pig2Frames = new ArrayList<>();
        importImages(1, 10, pigFrames);
        importImages(11, 12, catFrames);

        importImages(13, 23, pig2Frames);
        pressedKeys = new boolean[128];
        time = 0;
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
        animateCat = new Animation(catFrames, 1);
        animatePig = new Animation(pigFrames, 1);
        animatePig2 = new Animation(pig2Frames, 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);  // the order that things get "painted" matter; we put background down first
        g.drawImage(animateCat.getActiveFrame(), 200, 100, null);
        g.drawImage(animatePig.getActiveFrame(), 100, 100, null);
        g.drawImage(animatePig2.getActiveFrame(), 300, 100, null);

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

    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+","+y);//these co-ords are relative to the component
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


