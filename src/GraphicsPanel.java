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
    private ArrayList<Predator> predators;
    private ArrayList<Predator> predators2;
    private BufferedImage background;
    private Timer timer;
    private int time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> balloonFrames;
    private ArrayList<BufferedImage> catFrames;
    private ArrayList<BufferedImage> pig2Frames;
    private ArrayList<InvisibleRect> invisibleRects;
    private ArrayList<InvisibleRect> invisibleRects2;
    private JButton predator1Button;
    private JButton predator2Button;
    private JButton predator3Button;
    private JButton predator4Button;
    private JButton predator5Button;
    private JButton predator6Button;
    private Boolean holdingMouse;
    private Balloon b;
    private int idx;

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
        b = new Balloon(balloonFrames, 1, true);

        balloons = new ArrayList<>();
        balloons.add(b);
        balloons.add(b);

        balloons2 = new ArrayList<>();
        balloons2.add(b);
        idx = 0;
        time = 100;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();

        setFocusable(true); // this line of code + one below makes this panel active for key listener events
        requestFocusInWindow(); // see comment above

        addMouseListener(this);

        animateCat = new Animation(catFrames, 10);
        animatePig = new Animation(pigFrames, 10);
        animatePig2 = new Animation(pig2Frames, 10);

        // adds all the buttons 1-3 left side 4-6 right side
        predator1Button = addPredatorButton("src/assets/predator1.png");
        predator2Button = addPredatorButton("src/assets/predator11.png");
        predator3Button = addPredatorButton("src/assets/predator13.png");
        predator4Button = addPredatorButton("src/assets/predator1.png");
        predator5Button = addPredatorButton("src/assets/predator11.png");
        predator6Button = addPredatorButton("src/assets/predator13.png");

        holdingMouse = false;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);  // just do this

        checkTurns(g, invisibleRects, balloons);
        checkTurns(g, invisibleRects2, balloons2);
        if(time/10 == 0){

        }


        g.drawImage(background, 0, 0, null);

        // moves buttons to right location
        predator1Button.setLocation(2, 15);
        predator2Button.setLocation(2, 140);
        predator3Button.setLocation(2, 247);
        predator4Button.setLocation(1064, 15);
        predator5Button.setLocation(1064, 140);
        predator6Button.setLocation(1064, 247);

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

    //gets index of balloon to be shot in array list
    public int shootIdx(ArrayList<Balloon> b, ArrayList<Predator> p){
        int choice = 0;
        int distance = 100000;
        for (int i = 1; i < b.size(); i++){
            int x1 = b.get(choice).getX();
            int y1 = b.get(choice).getY();
            int dist2;
            for(int j = 1; j < p.size(); j++){
                Predator pred = p.get(j);
                if(pred.get)

            }



        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        holdingMouse = true;
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "," + y);//these co-ords are relative to the component
        holdingMouse = false;

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
        } else if (e.getSource() instanceof JButton && holdingMouse == true) {
            System.out.println("Holding mouse and click buttoning");
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

    private JButton addPredatorButton(String fileLocation) {
        JButton predator = new JButton(new ImageIcon(fileLocation));
        predator.addActionListener(this);
        add(predator);
        return predator;
    }

}


