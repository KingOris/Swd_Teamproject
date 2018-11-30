import Database.DataBase_Con;
import Database.DataBase_op;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sign_up_GUI extends JFrame implements ActionListener {
    private JTextField IDField = new JTextField(10);
    private JPasswordField pWField = new JPasswordField(10);
    private JPasswordField cPWField = new JPasswordField(10);
    private JTextField eField = new JTextField(10);
    private JTextField pNField = new JTextField(10);
    private JComboBox bar = generateBar();
    private String buyerOrSeller = "";
    private JButton signUp = new JButton("Sign Up");
    DataBase_Con myDB = new DataBase_Con();
    public DataBase_op myOpr=new DataBase_op(myDB);
    public Sign_up_GUI(){
        super("Register");
        initialSignUp();
        signUp.addActionListener(this);
        bar.addActionListener(this);

        this.setVisible(true);
        this.setSize(400,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initialSignUp(){

        JPanel panel = new JPanel(new GridLayout(6,2));
        JLabel ID = new JLabel("ID: ");
        ID.setHorizontalAlignment(JLabel.RIGHT);
        JLabel password = new JLabel("Password");
        password.setHorizontalAlignment(JLabel.RIGHT);
        JLabel confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setHorizontalAlignment(JLabel.RIGHT);
        JLabel email = new JLabel("Email: ");
        email.setHorizontalAlignment(JLabel.RIGHT);
        JLabel phoneNum = new JLabel("Phone Number: ");
        phoneNum.setHorizontalAlignment(JLabel.RIGHT);



        panel.add(ID);
        panel.add(IDField);
        panel.add(password);
        panel.add(pWField);
        panel.add(confirmPassword);
        panel.add(cPWField);
        panel.add(email);
        panel.add(eField);
        panel.add(phoneNum);
        panel.add(pNField);
        panel.add(bar);
        panel.add(signUp);

        this.add(panel);
    }

    private JComboBox<String> generateBar(){
        String[] choice = {"Buyer", "Seller", "Seller and Buyer"};
        JComboBox<String> bar = new JComboBox<>(choice);
        bar.setSelectedIndex(1);

        return bar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bar){
            JComboBox cb = (JComboBox)e.getSource();
            String msg = (String)cb.getSelectedItem();
            if (msg != null) {
                switch(msg){
                    case "Buyer" :
                        buyerOrSeller = "Buyer";
                    break;
                    case "Seller" :
                        buyerOrSeller = "Seller";
                    break;
                    case "Seller and Buyer" :
                        buyerOrSeller = "Seller and Buyer";
                }
            }
        }else if(e.getSource() == signUp){
            String Id = IDField.getText();
            String password = pWField.getText();
            String password1 = cPWField.getText();
            String emailAdd = eField.getText();
            String phone = pNField.getText();
            String  type = buyerOrSeller;
            if(!password.equals(password1)){
                JOptionPane.showMessageDialog(null, "Password confirmation is wrong","Tip",2);
                pWField.setText("");
                cPWField.setText("");

            }else{
                myOpr.insertData(Id,password,emailAdd,phone,type);
                setVisible(false);
                Login_GUI log = new Login_GUI();
                log.setVisible(true);
                log.setSize(400, 400);
                log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                log.setResizable(false);
            }
        }
    }

}


