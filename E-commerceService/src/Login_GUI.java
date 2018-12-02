import Database.DataBase_op;
import Database.DataBase_Con;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login_GUI extends JFrame {
    private JLabel user = new JLabel("     ID:      ");
    private JLabel password = new JLabel("Password:");
    private JTextField userID = new JTextField();
    private JPasswordField userPassword = new JPasswordField();
    private JButton userlog = new JButton("Log In");
    private JButton register = new JButton("Register");
    private String idSave;
    private String passSave;
    DataBase_Con myDB = new DataBase_Con();
    public DataBase_op myOpr=new DataBase_op(myDB);
    public Login_GUI(){
        super("Log In");
        //userIndex = -1;
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
        ButtonHandler button= new ButtonHandler();
        userlog.addActionListener(button);
        register.setPreferredSize(new Dimension(140, 50));
        register.addActionListener(button);
        panel1.add(userlog);
        panel1.add(register);
        mainPanel.add(panel);
        mainPanel.add(panel2);
        mainPanel.add(panel1);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Window Closed");
                MainPage_GUI openMain = new MainPage_GUI();
                openMain.showMain(myOpr.getUserIndex(idSave));
            }
        });
        add(mainPanel);
    }
    public static void main(String args[]){
        /*Login_GUI log = new Login_GUI();
        log.setVisible(true);
        log.setSize(400, 400);
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.setResizable(false);*/
    }

    public class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == userlog){
                idSave = userID.getText();
                passSave = userPassword.getText();
                if(myOpr.selectName(idSave)){//登录判断
                    if(myOpr.selectPassword(passSave)){
                        MainPage_GUI lalal = new MainPage_GUI();
                        lalal.showMain(myOpr.getUserIndex(idSave));
                        lalal.setUserIndex(myOpr.getUserIndex(idSave));
                        System.out.println("This is user ID after log" + lalal.getUserIndex());

                        lalal.logIn.setEnabled(false);
                        dispose();
                        //setVisible(false);//登录成功则关闭界面
                    }else{
                        JOptionPane.showMessageDialog(null, "Password is wrong","Tip",2);
                        userPassword.setText("");
                        myOpr.setNumber1();//密码错误将number置0
                        myOpr.setNumber2();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "This Id is not registered","Tip",2);
                    userID.setText("");
                    userPassword.setText("");
                }

            }
            if(e.getSource() == register){
                Sign_up_GUI sign = new Sign_up_GUI();
                setVisible(false);
            }

        }
    }
}


