package sk.tomsik68.oneclickmc;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OneClickMCMainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    // private final LoginPanel loginPanel;
    private final GameLauncher launcher;
    private JButton btnPlay;
    private static OneClickMCMainFrame instance;

    public OneClickMCMainFrame(GameLauncher l) {
        launcher = l;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        centerFrame(150, 60);
        setTitle("1-Click MC");

        add(btnPlay = new JButton("Play"));
        btnPlay.addActionListener(this);
    }

    private void centerFrame(int targetW, int targetH) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        setBounds(tk.getScreenSize().width / 2 - targetW / 2, tk.getScreenSize().height / 2 - targetH / 2, targetW, targetH);
    }

    public static void main(String[] args) {
        GameLauncher launcher = new GameLauncher();
        instance = new OneClickMCMainFrame(launcher);
        instance.setVisible(true);
    }

    public static void close() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnPlay)
            try {
                launcher.play(0);
            } catch (Exception exc) {
                exc.printStackTrace();
                JOptionPane.showMessageDialog(this, exc.getMessage(), "Can't launch!", JOptionPane.ERROR_MESSAGE);
            }
    }

}
