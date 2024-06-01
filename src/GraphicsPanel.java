import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements MouseListener, ActionListener, Runnable {
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
    private Boolean holdingMouse;
    private Balloon b;
    private int idx;
    private ArrayList<PredatorButton> predatorButtons;
    private int predatorNumDragged;

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
        balloons2.add(new Balloon(balloonFrames, 1, false));

        idx = 0;
        time = 100;
        timer = new Timer(1000, this);
        timer.start();

        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(this);

        predatorButtons = new ArrayList<>();
        // creates objects that will serve as buttons to place predators
        predatorButtons.add(new PredatorButton(2, 15, 1 ));
        predatorButtons.add(new PredatorButton(2, 140, 11 ));
        predatorButtons.add(new PredatorButton(2, 247, 13 ));

        predatorButtons.add(new PredatorButton(1064, 15, 1 ));
        predatorButtons.add(new PredatorButton(1064, 140, 11 ));
        predatorButtons.add(new PredatorButton(1064, 247, 13 ));

        holdingMouse = false;
        predatorNumDragged = -1;
        predators = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);  // just do this

        checkTurns(g, invisibleRects, balloons);
        checkTurns(g, invisibleRects2, balloons2);

        if (time/10 == 0) {}

        g.drawImage(background, 0, 0, null);

        for (Predator predator : predators) {
            g.drawImage(predator.getActiveFrame(), predator.getX(), predator.getY(), null);
        }

        //moves the balloons
        for (Balloon balloon : balloons) {
            balloon.move();
            g.drawImage(balloon.getActiveFrame(), balloon.getX(), balloon.getY(), null);

        }
        for (Balloon balloon : balloons2) {
            balloon.move();
            g.drawImage(balloon.getActiveFrame(), balloon.getX(), balloon.getY(), null);
        }

        // draws the buttons used to place predators
        for (PredatorButton button : predatorButtons) {
            g.drawImage(button.getImage(), button.getX(), button.getY(), null);
        }

        // for dragging predators and placing them
        if (predatorNumDragged >= 0) {
            g.drawImage(predatorButtons.get(predatorNumDragged).getImage(), MouseInfo.getPointerInfo().getLocation().x - 150, MouseInfo.getPointerInfo().getLocation().y - 200, null);
        }
    }

    //gets index of predators who can shoot ( their distance is 200 or less from a balloon)
    public ArrayList<Integer> shootIdx(ArrayList<Balloon> b, ArrayList<Predator> p){
        //this
        ArrayList<Integer> indexes = new ArrayList<>();
        double distance;
        int x2;
        int y2;
        int x1;
        int y1;

        for (int i = 1; i < b.size(); i++){
            x1 = b.get(i).getX();
            y1 = b.get(i).getY();
            for(int j = 1; j < p.size(); j++){
                Predator pred = p.get(j);
                x2 = pred.getX();
                y2 = pred.getY();
                distance = Math.sqrt((double)((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1)));
            }
        }
        return indexes;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = 0;
        for (PredatorButton button: predatorButtons) {
            Boolean dragged = checkClickedImaged(button.getX(), button.getY(), button.getImage().getWidth(), button.getImage().getHeight(), e.getPoint());
            if (dragged) {
                predatorNumDragged = i;
            }
            i++;
        }
    }

    public void mouseReleased(MouseEvent e) {
        holdingMouse = false;
        if (predatorNumDragged >= 0) {
            ArrayList<BufferedImage> animalFrames = pig2Frames;
            if (predatorNumDragged == 0 || predatorNumDragged == 3) {
                animalFrames = pigFrames;
            } else if (predatorNumDragged == 1 || predatorNumDragged == 4) {
                animalFrames = catFrames;
            }
            predators.add(new Predator(animalFrames, MouseInfo.getPointerInfo().getLocation().x - 150, MouseInfo.getPointerInfo().getLocation().y - 200));
            predatorNumDragged = -1;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time += .1;
        }
    }

    @Override
    public void run() {}

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

    private Boolean checkClickedImaged(int x, int y, int width, int height, Point point) {
        if (new Rectangle(x, y, width, height).contains(point)) {
            holdingMouse = true;
            return true;
        }
        return false;
    }
}


