import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

//The class is to register the sell items
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
        priceL = new JLabel("Price");
        number = new JLabel("The number of items");
        itemname = new JTextField();
        price = new JTextField();
        num = new JTextField();
        register = new JButton("Register");
        readImage = new JButton("Choose Image");
        nametext = new String();
        image = new JLabel();
        numberofitem = 0;


        //name.setFont(new Font(null,Font.PLAIN,30));
        //priceL.setFont(new Font(null,Font.PLAIN,30));
        //number.setFont(new Font(null,Font.PLAIN,30));
        panel.setLayout(new GridBagLayout());

        ButtonHandler handler = new ButtonHandler();
        ReadHandler readHandler = new ReadHandler();

        JPanel newpanel = new JPanel(new GridLayout(4,2,10,10));

        panel.add(image,setGridBagConstraints(1,4,1,1,0));
        newpanel.add(name);
        newpanel.add(itemname);
        newpanel.add(priceL);
        newpanel.add(price);

        newpanel.add(number);
        newpanel.add(num);

        panel.add(newpanel,setGridBagConstraints(0,3,1,1,1));
        newpanel.add(readImage);
        register.addActionListener(handler);
        newpanel.add(register);
        readImage.addActionListener(readHandler);


        add(panel);
    }

    //The ActionListener inner class for register button
    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            nametext = itemname.getText();
            pr = new BigDecimal(price.getText());
            numberofitem = Integer.parseInt(num.getText());
            MainPage_GUI mainGUI = new MainPage_GUI();
            if(nametext == null || pr == null || numberofitem == 0){
                JOptionPane.showMessageDialog(null,"Product Information are not complete and quantity can not be 0");
            }else{
                mainGUI.uploadItems(nametext,pr.toString(),numberofitem,bufferedImage);
            }
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

    //The actionListerner inner class for updateing the image
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
            image.setIcon(new ImageIcon(bufferedImage.getScaledInstance(200,200,Image.SCALE_DEFAULT)));
        }
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    //The GridBagConstraints to set up the GUI
    private GridBagConstraints setGridBagConstraints(int width,int height, int weightx, int weighty, int num){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(num == 0){
            gridBagConstraints.fill = GridBagConstraints.BOTH;
        }else if(num == 1){
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        }else {
            gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        }
        gridBagConstraints.gridwidth = width;
        gridBagConstraints.gridheight = height;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        return gridBagConstraints;
    }
}
