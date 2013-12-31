package sk.tomsik68.oneclickmc;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JTextField tfLogin;
    private final JPasswordField pfPassword;
    private final JButton btnOk;

    public LoginPanel() {
        super(new FlowLayout(FlowLayout.CENTER));
        add(new JLabel("Login:   "));
        add(tfLogin = new JTextField(16));
        add(new JLabel("Password:"));
        add(pfPassword = new JPasswordField(16));
        btnOk = new JButton("Ok");
        add(btnOk);
    }

    public void addListener(ActionListener listener) {
        btnOk.addActionListener(listener);
    }

    public String getLogin() {
        return tfLogin.getText();
    }

    public char[] getPassword() {
        return pfPassword.getPassword();
    }

}
