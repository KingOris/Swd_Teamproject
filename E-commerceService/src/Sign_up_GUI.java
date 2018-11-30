import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sign_up_GUI extends JFrame implements ActionListener {
    private JTextField IDField = new JTextField(10);
    private JPasswordField pWField = new JPasswordField(10);
    private JPasswordField cPWField = new JPasswordField(10);
    private JTextField eField = new JTextField(10);
    private JTextField pNField = new JTextField(10);
    private JComboBox bar = generateBar();
    private int buyerOrSeller = 0;
    private JButton signUp = new JButton("Sign Up");
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
        String[] choice = {"Buyer", "Seller", "Seller and Buyer", "da sha gua"};
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
                        buyerOrSeller = 1;
                    break;
                    case "Seller" :
                        buyerOrSeller = 2;
                    break;
                    case "Seller and Buyer" :
                        buyerOrSeller = 3;
                }
            }
        }else if(e.getSource() == signUp){
            signUp.setText("Clicked");
        }
    }


   /* public static void main(String args[]){
        new Sign_up_GUI();

    }*/
}


