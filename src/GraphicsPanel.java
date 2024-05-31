import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements MouseListener, ActionListener, Runnable {
    private Animation animateCat;
    private Animation animatePig;
    private Animation animatePig2;
    private ArrayList<Balloon> balloons;
    private ArrayList<Balloon> balloons2;
    private BufferedImage background;
    private Timer timer;
    private double time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> balloonFrames;
    private ArrayList<BufferedImage> catFrames;
    private ArrayList<BufferedImage> pig2Frames;
    private ArrayList<InvisibleRect> invisibleRects;
    private ArrayList<InvisibleRect> invisibleRects2;
    private JButton predator1;
    private Icon predator1Icon;

    public GraphicsPanel(String name) {

        // adds rectangles with the coordinates of the corners of path for left side
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

        // adds rectangles with the coordinates of the corners of path for left side
        invisibleRects2 = new ArrayList<>();
        for (InvisibleRect rect: invisibleRects) {
            invisibleRects2.add(new InvisibleRect(583 + (583 - rect.getX()), rect.getY()));
        }

        // importing all images
        try {
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
        balloons.add(new Balloon(balloonFrames, 1, true));
        balloons.add(new Balloon(balloonFrames, 1, true));

        balloons2 = new ArrayList<>();
        balloons2.add(new Balloon(balloonFrames, 1, false));

        setFocusable(true); // this line of code + one below makes this panel active for key listener events
        requestFocusInWindow(); // see comment above

        addMouseListener(this);

        animateCat = new Animation(catFrames, 1);
        animatePig = new Animation(pigFrames, 1);
        animatePig2 = new Animation(pig2Frames, 1);

        predator1Icon = new ImageIcon("src/assets/predator1.png");
        predator1 = new JButton(predator1Icon);
        add(predator1);
        predator1.addActionListener(this);
        predator1.setLocation(50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);

        checkTurns(g, invisibleRects, balloons);
        checkTurns(g, invisibleRects2, balloons2);

        // animation
        g.drawImage(animateCat.getActiveFrame(), 200, 100, null);
        g.drawImage(animatePig.getActiveFrame(), 100, 100, null);
        g.drawImage(animatePig2.getActiveFrame(), 300, 100, null);

        //moves the balloons
        for (Balloon balloon : balloons) {
            balloon.move();
            g.drawImage(balloon.getActiveFrame(), balloon.getX(), balloon.getY(), null);
        }

        for (Balloon balloon : balloons2) {
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

    // controls when the balloon turns
    private void checkTurns(Graphics g, ArrayList<InvisibleRect> invisibleRects, ArrayList<Balloon> balloons) {
        for (InvisibleRect invisibleRect : invisibleRects) {
            g.drawRect(invisibleRect.getX(), invisibleRect.getY(), 20, 20);
            for (Balloon balloon : balloons) {
                if (balloon.getRect().intersects(invisibleRect.getRect()) && balloon.getTime() >= 1.5) {
                    balloon.incrementTurnNum();
                    balloon.zeroTime();
                }
            }
        }
    }

}


