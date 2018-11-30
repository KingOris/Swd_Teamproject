import javax.swing.*;
import java.awt.*;

public class Selling_GUI extends JFrame {
    private JTextField itemname;
    private JTextField price;
    private JTextField num;
    private JButton register;
    private JLabel name;
    private JLabel priceL;
    private JLabel number;

    public Selling_GUI(){
        super("Register Item");
        JPanel panel = new JPanel();
        name = new JLabel("Item Name");
        priceL = new JLabel("Price");
        number = new JLabel("The number of items");
        itemname = new JTextField(10);
        price = new JTextField(10);
        num = new JTextField(10);
        register = new JButton("Register");

        name.setFont(new Font(null,Font.PLAIN,30));
        priceL.setFont(new Font(null,Font.PLAIN,30));
        number.setFont(new Font(null,Font.PLAIN,30));
        panel.setLayout(new GridLayout(0,2,3,3));

        panel.add(name);
        panel.add(itemname);

        panel.add(priceL);
        panel.add(price);

        panel.add(number);
        panel.add(num);

        panel.add(register);

        add(panel);
        setVisible(true);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    public static void main(String args[]){
        Selling_GUI sell = new Selling_GUI();
    }
}
