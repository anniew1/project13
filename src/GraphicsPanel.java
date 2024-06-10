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
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> bullets2;
    private BufferedImage background;
    private BufferedImage bullet;
    private Timer balloonTimer;
    private int time;
    private ArrayList<BufferedImage> pigFrames;
    private ArrayList<BufferedImage> balloonFrames;
    private ArrayList<BufferedImage> catFrames;
    private ArrayList<BufferedImage> pig2Frames;
    private ArrayList<InvisibleRect> invisibleRects;
    private ArrayList<InvisibleRect> invisibleRects2;
    private ArrayList<PredatorButton> predatorButtons;
    private int predatorNumDragged;
    private int numBalloonsPerRound;
    private int balloonSpawned;
    private int balloonSpawned2;
    Player p1;
    Player p2;
    Player winner;

    public GraphicsPanel(String name) {

        // adds rectangles with the coordinates of the corners of path for left side
        invisibleRects = new ArrayList<>();
        invisibleRects.add(new InvisibleRect(201, 74));
        invisibleRects.add(new InvisibleRect(518, 67));
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
        for (InvisibleRect rect : invisibleRects) {
            invisibleRects2.add(new InvisibleRect(583 + (583 - rect.getX()), rect.getY()));
        }

        // importing all images
        try {
            background = ImageIO.read(new File("src/assets/background.png"));
            bullet = ImageIO.read(new File("src/assets/bullet.png"));
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

        balloons2 = new ArrayList<>();
        balloons2.add(new Balloon(balloonFrames, 1, false));

        balloonTimer = new Timer(2000, this);
        balloonTimer.start();
        time = 0;

        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(this);

        predatorButtons = new ArrayList<>();
        // creates objects that will serve as buttons to place predators
        predatorButtons.add(new PredatorButton(10, 15, 1));
        predatorButtons.add(new PredatorButton(10, 140, 11));
        predatorButtons.add(new PredatorButton(10, 247, 13));

        predatorButtons.add(new PredatorButton(1075, 15, 1));
        predatorButtons.add(new PredatorButton(1075, 140, 11));
        predatorButtons.add(new PredatorButton(1075, 247, 13));

        predatorNumDragged = -1;

        balloonSpawned = 0;
        balloonSpawned2 = 0;
        numBalloonsPerRound = 10;

        predators = new ArrayList<>();
        predators2 = new ArrayList<>();

        bullets = new ArrayList<>();
        bullets2 = new ArrayList<>();

        p1 = new Player("Player 1");
        p2 = new Player("Player 2");
        winner = null;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // checks to see if the balloon has to turn
        checkTurns(g, invisibleRects, balloons);
        checkTurns(g, invisibleRects2, balloons2);

        g.drawImage(background, 0, 0, null);

        // draws the players' money
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("$" + p1.getMoney(), 25, 500);
        g.drawString("$" + p2.getMoney(), 1080, 500);

        // draws the players' hp
        int p1Size = (int)(415 * (p1.getHp()/10.0));
        int p2Size = (int)(411 * (p2.getHp()/10.0));

        g.setColor(Color.GREEN);
        g.fillRect(108, 0, p1Size, 30);
        g.fillRect(650, 0, p2Size, 30);

        g.setColor(Color.RED);
        g.fillRect(108 + p1Size, 0, (int)(415 * ((10 - p1.getHp())/10.0)), 30);
        g.fillRect(650 + p2Size, 0, (int)(411 * ((10 - p2.getHp())/10.0)), 30);

        g.setColor(Color.black);
        g.drawString(p1.getName(), 260, 25);
        g.drawString(p2.getName(), 810, 25);

        // draws the time
        g.drawString("Time: " + time, 530, 50);

        // draws the predators
        for (Predator predator : predators) {
            g.drawImage(predator.getActiveFrame(), predator.getX(), predator.getY(), null);
        }

        for (Predator predator : predators2) {
            g.drawImage(predator.getActiveFrame(), predator.getX(), predator.getY(), null);
        }

        // draws the buttons used to place predators
        for (PredatorButton button : predatorButtons) {
            g.drawImage(button.getImage(), button.getX(), button.getY(), null);
        }

        if (winner == null) {

            moveBalloon(balloons, g, p1, p2);
            moveBalloon(balloons2, g, p2, p1);

            //gets index of predators who can shoot ( their distance is 200 or less from a balloon)
            //also gets index of balloon they correspond to

            ArrayList<Integer> b1Shoots = new ArrayList<>();
            ArrayList<Integer> b2Shoots = new ArrayList<>();
            ArrayList<Integer> slopes = new ArrayList<>();
            ArrayList<Integer> slopes2 = new ArrayList<>();
            ArrayList<Integer> p1Shoots = new ArrayList<>();
            ArrayList<Integer> p2Shoots = new ArrayList<>();
            double distance;
            int slope;
            int x2;
            int y2;
            int x1;
            int y1;
            if (predators.size() > 0 && balloons.size() > 0) {


                for (int i = 0; i < balloons.size(); i++) {
                    x1 = balloons.get(i).getX();
                    y1 = balloons.get(i).getY();
                    for (int j = 0; j < predators.size(); j++) {
                        Predator pred = predators.get(j);
                        x2 = pred.getX();
                        y2 = pred.getY();
                        distance = Math.sqrt((double) ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
                        slope = (x2-x1);
                        int sub = y2-y1;
                        if(sub!= 0){
                            slope/=sub;
                        }
                        if (distance <= 150) {
                            slopes.add(slope);
                            p1Shoots.add(j);
                            b1Shoots.add(i);
                        }
                    }
                }
                int horizontal = 58;
                int vertical = 36;
                for (int x = 0; x < bullets.size(); x++) {
                    g.drawImage(bullet, bullets.get(x).getX(), bullets.get(x).getY(), null);
                    //over here there will be code to make predators shoot a ball-shaped bullet

                    //ADD A FUNCTION THAT SAYS IF BULLETS LOCATION GREATER THAN X OR Y OF BACKGROUND, RESET ITS LOCATION TO ITS ORIginal
                    //so reset the location bak to p1Shoots.get(x).getX() + 56;
                    if(bullets.get(x).getX() > 583 || bullets.get(x).getY() < 0){
                        bullets.get(x).setX(predators.get(p1Shoots.get(x)).getX() + 56);
                        bullets.get(x).setY(predators.get(p1Shoots.get(x)).getY() + 38);
                    }
                    checkHit(bullets, balloons, p1);
                    bullets.get(x).setX(bullets.get(x).getX() + 1);
                    bullets.get(x).setY(bullets.get(x).getY() + slopes.get(x));
                    //go to mouse released method

                }
            }

            if (!predators2.isEmpty() && !balloons2.isEmpty()) {

                for (int i = 0; i < balloons2.size(); i++) {
                    x1 = balloons2.get(i).getX();
                    y1 = balloons2.get(i).getY();
                    for (int j = 0; j < predators2.size(); j++) {
                        Predator pred = predators2.get(j);
                        x2 = pred.getX();
                        y2 = pred.getY();
                        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                        if (distance <= 150) {
                            p2Shoots.add(j);
                            b2Shoots.add(i);
                        }
                    }
                }
                for (int x = 0; x < bullets2.size(); x++) {
                    g.drawImage(bullet, bullets2.get(x).getX(), bullets2.get(x).getY(), null);
                    //over here there will be code to make predators shoot a ball-shaped bullet

                    //ADD A FUNCTION THAT SAYS IF BULLETS LOCATION GREATER THAN X OR Y OF BACKGROUND, RESET ITS LOCATION TO ITS ORIginal
                    //so reset the location bak to p1Shoots.get(x).getX() + 56;
                    if(bullets2.get(x).getX() < 583 || bullets2.get(x).getY() < 0 ||bullets2.get(x).getX() > 1083){
                        bullets2.get(x).setX(predators2.get(p1Shoots.get(x)).getX() + 56);
                        bullets2.get(x).setY(predators2.get(p1Shoots.get(x)).getY() + 38);
                    }
                    checkHit(bullets2, balloons2, p2);
                    bullets2.get(x).setX(bullets2.get(x).getX() + 1);
                    bullets2.get(x).setY(bullets2.get(x).getY() + slopes.get(x));
                    //over here there will be code to make predators shoot a ball-shaped bullet
                }
            }

            // checks to see if bullet collided with balloon and removes if it does
//            checkHit(bullets, balloons, p1);
//            checkHit(bullets2, balloons2, p2);


            // for dragging predators and placing them
            if (predatorNumDragged >= 0) {
                g.drawImage(predatorButtons.get(predatorNumDragged).getImage(), MouseInfo.getPointerInfo().getLocation().x - 400, MouseInfo.getPointerInfo().getLocation().y - 400, null);
            }

        } else {
            g.drawString(winner.getName() + " wins!", 500, 300);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (winner == null) {
            int i = 0;
            for (PredatorButton button : predatorButtons) {
                Boolean dragged = checkClickedImaged(button.getX(), button.getY(), button.getImage().getWidth(), button.getImage().getHeight(), e.getPoint());
                if (dragged) {
                    predatorNumDragged = i;
                }
                i++;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (winner == null) {
            if (predatorNumDragged >= 0) {
                ArrayList<BufferedImage> animalFrames = pig2Frames;
                if (predatorNumDragged == 0 || predatorNumDragged == 3) {
                    animalFrames = pigFrames;
                } else if (predatorNumDragged == 1 || predatorNumDragged == 4) {
                    animalFrames = catFrames;
                }
                checkCoordinates(e.getX(), animalFrames);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (winner == null) {
            if (e.getSource() instanceof Timer balloonTimer) {
                if (balloonSpawned < numBalloonsPerRound) {
                    balloons.add(new Balloon(balloonFrames, 1, true));
                    balloonSpawned++;
                }

                if (balloonSpawned2 < numBalloonsPerRound) {
                    balloons2.add(new Balloon(balloonFrames, 1, false));
                    balloonSpawned2++;
                }
                time += 2;

                if (time % 30 == 0) {
                    balloonSpawned = 0;
                    balloonSpawned2 = 0;
                }
            }
        }
    }

    @Override
    public void run() {
    }


    // imports the images
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
                if (balloon.getRect().intersects(invisibleRect.getRect()) && balloon.getTime() >= 1.1) {
                    balloon.incrementTurnNum();
                    balloon.zeroTime();
                }
            }
        }
    }

    // checks to see if an image is being clicked on
    private Boolean checkClickedImaged(int x, int y, int width, int height, Point point) {
        if (new Rectangle(x, y, width, height).contains(point)) {

            return true;
        }
        return false;
    }

    // checks to see what side to add predator to and subtracts the money for corresponding side
    private void checkCoordinates(int x, ArrayList<BufferedImage> animalFrames) {
        boolean rightSide = true;

        if (predatorNumDragged >= 3) {
            rightSide = false;
        }
        //each predator is assigned its own bullet with its own locations

        if (x <= 550 && rightSide) {
            if (p1.getMoney() >= 400) {
                predators.add(new Predator(animalFrames, MouseInfo.getPointerInfo().getLocation().x - 400, MouseInfo.getPointerInfo().getLocation().y - 400));
                p1.loseMoney(400);
                bullets.add(new Bullet(predators.get(predators.size() - 1).getX() + 58, predators.get(predators.size() - 1).getY() + 36));
            }
        } else if (x >= 615 && !rightSide) {
            if (p2.getMoney() >= 400) {
                predators2.add(new Predator(animalFrames, MouseInfo.getPointerInfo().getLocation().x - 400, MouseInfo.getPointerInfo().getLocation().y - 400));
                p2.loseMoney(400);
                bullets2.add(new Bullet(predators2.get(predators2.size() - 1).getX() + 58, predators2.get(predators2.size() - 1).getY() + 36));
            }
        }

        predatorNumDragged = -1;
    }

    // moves balloon and checks for winner
    public void moveBalloon(ArrayList<Balloon> balloon, Graphics g, Player p, Player opposite) {
        for (int j = 0; j < balloon.size(); j++) {
            Boolean lose = false;

            if (balloon.get(j).move()) {
                lose = p.loseHp();
                balloon.remove(j);
                j--;
            } else {
                g.drawImage(balloon.get(j).getActiveFrame(), balloon.get(j).getX(), balloon.get(j).getY(), null);
            }

            if (lose) {
                winner = opposite;
            }
        }
    }

    // checks to see if bullet collides with balloon
    public void checkHit(ArrayList<Bullet> bulletList, ArrayList<Balloon> balloonList, Player player) {
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < balloonList.size(); j++) {
                if (bulletList.get(i).getRect().intersects(balloonList.get(i).getRect())) {
                    balloonList.remove(j);
                    j--;
                    player.addMoney();
                }
            }
        }
    }
}



