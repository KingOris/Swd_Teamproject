import javax.swing.*;
import java.awt.*;

public class Login_GUI extends JFrame {
    private JTextField userID = new JTextField();
    private JPasswordField userPassword = new JPasswordField();
    private JButton userlog = new JButton("Log In");
    public Login_GUI(){
        super("Log In");
        JPanel panel;
        panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        panel.setLayout(layout);
        userID.setPreferredSize(new Dimension(300, 50));
        userPassword.setPreferredSize(new Dimension(300, 50));
        panel.add(userID);
        panel.add(userPassword);

        JPanel panel1 = new JPanel();
        panel1.setLayout(layout);
        userlog.
    }
}
