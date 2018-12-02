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
        priceL = new JLabel("Price");
        number = new JLabel("The number of items");
        itemname = new JTextField();
        price = new JTextField();
        num = new JTextField();
        register = new JButton("Register");
        readImage = new JButton("Submit Image");
        nametext = new String();
        image = new JLabel();
        numberofitem = 0;


        //name.setFont(new Font(null,Font.PLAIN,30));
        //priceL.setFont(new Font(null,Font.PLAIN,30));
        //number.setFont(new Font(null,Font.PLAIN,30));
        panel.setLayout(new GridBagLayout());

        ButtonHandler handler = new ButtonHandler();
        ReadHandler readHandler = new ReadHandler();

        panel.add(image,setGridBagConstraints(1,4,1,1));
        panel.add(name,setGridBagConstraints(1,1,1,1));
        panel.add(itemname,setGridBagConstraints(0,1,1,1));
        panel.add(priceL,setGridBagConstraints(1,1,1,1));
        panel.add(price,setGridBagConstraints(0,1,1,1));

        panel.add(number,setGridBagConstraints(1,1,1,1));
        panel.add(num,setGridBagConstraints(0,1,1,1));

        panel.add(readImage,setGridBagConstraints(2,1,1,1));
        register.addActionListener(handler);
        panel.add(register,setGridBagConstraints(0,1,1,1));
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

            image.setIcon(new ImageIcon(bufferedImage.getScaledInstance(200,200,Image.SCALE_DEFAULT)));
        }
    }

    private BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    private GridBagConstraints setGridBagConstraints(int width,int height, int weightx, int weighty){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = width;
        gridBagConstraints.gridheight = height;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        return gridBagConstraints;
    }
    public static void main(String args[]){
        Selling_GUI sell = new Selling_GUI();

        sell.setSize(500,500);
        sell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sell.setResizable(true);
        sell.setVisible(true);
    }
}
