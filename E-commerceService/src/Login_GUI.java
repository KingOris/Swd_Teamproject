import javax.swing.*;
import java.awt.*;

public class Login_GUI extends JFrame {
    private JLabel user = new JLabel("     ID:      ");
    private JLabel password = new JLabel("Password:");
    private JTextField userID = new JTextField();
    private JPasswordField userPassword = new JPasswordField();
    private JButton userlog = new JButton("Log In");
    private JButton register = new JButton("Register");
    public Login_GUI(){
        super("Log In");
        JPanel mainPanel = new JPanel();

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        panel.setLayout(layout);
        userID.setPreferredSize(new Dimension(300, 50));
        panel.add(user);
        panel.add(userID);


        JPanel panel2 = new JPanel();
        userPassword.setPreferredSize(new Dimension(300, 50));
        panel2.add(password);
        panel2.add(userPassword);


        JPanel panel1 = new JPanel();
        panel1.setLayout(layout);
        userlog.setPreferredSize(new Dimension(140, 50));
        register.setPreferredSize(new Dimension(140, 50));
        panel1.add(userlog);
        panel1.add(register);

        mainPanel.add(panel);
        mainPanel.add(panel2);
        mainPanel.add(panel1);

        add(mainPanel);
    }
    public static void main(String args[]){
        Login_GUI log = new Login_GUI();
        log.setVisible(true);
        log.setSize(400, 400);
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.setResizable(false);
    }
}


