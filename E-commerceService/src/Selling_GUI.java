import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class Selling_GUI extends JFrame {
    private JTextField itemname;
    private JTextField price;
    private JTextField num;
    private JButton register;
    private JLabel name;
    private JLabel priceL;
    private JLabel number;
    private JButton readImage;
    private String nametext;
    private BigDecimal pr;
    private int numberofitem;
    BufferedImage bufferedImage;
    private JLabel image;

    public Selling_GUI(){
        super("Register Item");
        JPanel panel = new JPanel();
        name = new JLabel("Item Name");
        name.setHorizontalAlignment(JLabel.RIGHT);
        priceL = new JLabel("Price");
        priceL.setHorizontalAlignment(JLabel.RIGHT);
        number = new JLabel("The number of items");
        number.setHorizontalAlignment(JLabel.RIGHT);
        itemname = new JTextField(10);
        price = new JTextField(10);
        num = new JTextField(10);
        register = new JButton("Register");
        readImage = new JButton("Submit Image");
        nametext = new String();
        image = new JLabel();
        numberofitem = 0;


        JLabel block = new JLabel();

        //name.setFont(new Font(null,Font.PLAIN,30));
        //priceL.setFont(new Font(null,Font.PLAIN,30));
        //number.setFont(new Font(null,Font.PLAIN,30));
        panel.setLayout(new GridLayout(0,3,10,10));

        ButtonHandler handler = new ButtonHandler();
        ReadHandler readHandler = new ReadHandler();

        panel.add(block);
        panel.add(name);
        panel.add(itemname);

        panel.add(image);
        panel.add(priceL);
        panel.add(price);

        panel.add(new JLabel());
        panel.add(number);
        panel.add(num);

        panel.add(new JLabel());
        panel.add(readImage);
        register.addActionListener(handler);
        panel.add(register);
        readImage.addActionListener(readHandler);


        add(panel);
    }

    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            nametext = itemname.getText();
            pr = new BigDecimal(price.getText());
            numberofitem = Integer.parseInt(num.getText());
        }
    }

    public int getNumberofitem(){
        return numberofitem;
    }

    public BigDecimal getPr(){
        return pr;
    }

    public String getNametext(){
        return nametext;
    }

    private class ReadHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showDialog(new JLabel(),"Choose image");
            File file = fileChooser.getSelectedFile();
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            image.setIcon(new ImageIcon(bufferedImage));
        }
    }

    private BufferedImage getBufferedImage(){
        return bufferedImage;
    }
    public static void main(String args[]){
        Selling_GUI sell = new Selling_GUI();

        sell.setSize(500,150);
        sell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sell.setResizable(true);
        sell.setVisible(true);
    }
}
