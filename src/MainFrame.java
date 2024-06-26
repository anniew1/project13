import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {

    private GraphicsPanel panel;
    private Timer timer;

    public MainFrame(String name) {
        JFrame frame = new JFrame("Balloon Wars");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1186, 600); // 540 height of image + 40 for window menu bar
        frame.setLocationRelativeTo(null); // auto-centers frame in screen

        // create and add panel
        panel = new GraphicsPanel(name);
        frame.add(panel);

        // display the frame
        frame.setVisible(true);

        timer = new Timer(1, this);
        timer.start();

        //Sound initialized
        SoundPlayer soundPlayer = new SoundPlayer();
        String soundFilePath = "src/assets/theme.wav"; // Change this to your sound file's path
        while(true) {
            soundPlayer.playSound(soundFilePath);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            panel.repaint();
        }
    }

}

