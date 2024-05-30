import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener, Runnable {
    private Animation animateCat;
    private Animation animatePig;
    private Animation animatePig2;
    private ArrayList<Balloon> balloons;
    private BufferedImage background;
    private boolean[] pressedKeys;
    private Timer timer;
    private double time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> balloonFrames;
    private ArrayList<BufferedImage> catFrames;
    private ArrayList<BufferedImage> pig2Frames;
    private ArrayList<InvisibleRect> invisibleRects;
    private ArrayList<InvisibleRect> invisibleRects2;

    public GraphicsPanel(String name) {

        invisibleRects = new ArrayList<>();

        invisibleRects.add(new InvisibleRect(201, 74));
        invisibleRects.add(new InvisibleRect(518, 63));
        invisibleRects.add(new InvisibleRect(518, 175));
        invisibleRects.add(new InvisibleRect(376, 140));
        invisibleRects.add(new InvisibleRect(419, 296));
        invisibleRects.add(new InvisibleRect(285, 275));
        invisibleRects.add(new InvisibleRect(308, 397));
        invisibleRects.add(new InvisibleRect(189, 384));
        invisibleRects.add(new InvisibleRect(198, 502));
        invisibleRects.add(new InvisibleRect(518, 480));

        invisibleRects2 = new ArrayList<>();

        for (InvisibleRect rect: invisibleRects) {
            invisibleRects2.add(new InvisibleRect(583 - rect.getX(), rect.getY()));
        }

        try {
            //background = ImageIO.read(new File("src/assets/dwasdwa.jpg"));
            background = ImageIO.read(new File("src/assets/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        pigFrames = new ArrayList<>();
        catFrames = new ArrayList<>();
        pig2Frames = new ArrayList<>();
        balloonFrames = new ArrayList<>();

        importImages(1, 10, pigFrames);
        importImages(11, 12, catFrames);
        importImages(13, 23, pig2Frames);
        importImages(43, 43, balloonFrames);

        balloons = new ArrayList<>();
        balloons.add(new Balloon(balloonFrames, 1));

        pressedKeys = new boolean[128];
        time = 0;

        timer = new Timer(100, this);
        timer.start();

        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above

        addKeyListener(this);
        addMouseListener(this);

        animateCat = new Animation(catFrames, 1);
        animatePig = new Animation(pigFrames, 1);
        animatePig2 = new Animation(pig2Frames, 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);

        for (InvisibleRect invisibleRect : invisibleRects) {
            g.drawRect(invisibleRect.getX(), invisibleRect.getY(), 20, 20);
            for (Balloon balloon : balloons) {
                if (balloon.getRect().intersects(invisibleRect.getRect()) && time >= 1.5) {
                    balloon.incrementTurnNum();
                    System.out.println(balloon.getTurnNum());
                    time = 0;
                }
            }
        }

        g.drawImage(animateCat.getActiveFrame(), 200, 100, null);
        g.drawImage(animatePig.getActiveFrame(), 100, 100, null);
        g.drawImage(animatePig2.getActiveFrame(), 300, 100, null);

        for (Balloon balloon : balloons) {
            balloon.move();
            g.drawImage(balloon.getActiveFrame(), balloon.getX(), balloon.getY(), null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "," + y);//these co-ords are relative to the component
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time += .1;
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

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


