
import Database.DataBase_op;
import Database.DataBase_Con;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class MainPage_GUI extends JFrame implements ActionListener {
    private int goodsNumber;
    private JPanel mainPanel;

    // crate products detail panel
    private JLabel[] goodsName;
    private JLabel[] goodsPrice;
    private JLabel[] goodsQuality;
    private JButton[] goodsBuy;
    private JPanel[] panel;
    private JButton[] contact;
    private JButton[] detail;
    private JLabel[] icon;


    private JTextField searchBar = new JTextField(20);
    private JButton search = new JButton("Search");


    public JButton logIn;
    private JButton sell = new JButton("sell");
    private JButton myChart = new JButton("My Chart");

    private DataBase_Con myDB = new DataBase_Con();
    private DataBase_op myOpr=new DataBase_op(myDB);

    public int getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }

    private int userIndex = -1;



    MainPage_GUI(){
        super("Main Page");
    }

    public void showMain(int userIndex){
        String log = logInOrNot(userIndex);
        logIn = new JButton(log);
        goodsNumber = myOpr.getGoodNum();
        mainPanel = new JPanel(new GridLayout(goodsNumber+3,1));
        goodsName = new JLabel[goodsNumber];
        goodsPrice = new JLabel[goodsNumber];
        goodsQuality = new JLabel[goodsNumber];
        goodsBuy =  new JButton[goodsNumber];
        panel = new JPanel[goodsNumber];
        contact = new JButton[goodsNumber];
        detail = new JButton[goodsNumber];
        icon = new JLabel[goodsNumber];
        logIn.addActionListener(this);
        myChart.addActionListener(this);
        sell.addActionListener(this);
        search.addActionListener(this);
        addLogIn();
        addSearchBar();
        addTittle();
        initialMainPage(goodsNumber);
        JScrollPane scroll = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        this.setVisible(true);
        this.setSize(1200,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initialMainPage(int n){
        for(int i =0; i< n; i++){
            ImageIcon goodIcon = new ImageIcon(myOpr.getImage(i));
            //ImageIcon goodIcon = new ImageIcon(setIcon());
            Image resizedIcon = goodIcon.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
            //icon[i] = new JLabel(new ImageIcon(resizedIcon));
            icon[i] = new JLabel(new ImageIcon(resizedIcon));
            icon[i].setHorizontalAlignment(JLabel.CENTER);
            goodsName[i] = new JLabel();
            goodsName[i].setText(myOpr.getItemName(i));
            goodsName[i].setMaximumSize(new Dimension(100, 30));
            goodsPrice[i]= new JLabel();
            goodsPrice[i].setText(myOpr.getItemPrice(i));
            goodsQuality[i] = new JLabel();
            goodsQuality[i].setText(Integer.toString(myOpr.getItemAmount(i)));
            detail[i] = new JButton("Detail");
            detail[i].setMaximumSize(new Dimension(10,20));

            detail[i].addActionListener(this);
            goodsBuy[i] = new JButton("Add To Chart");
            goodsBuy[i].addActionListener(this);
            contact[i] = new JButton("Contact Seller");
            contact[i].addActionListener(this);


            panel[i] = new JPanel();
            panel[i].setLayout((new GridLayout(1,7)));
            panel[i].add(icon[i]);
            panel[i].add(goodsName[i]);
            panel[i].add(goodsPrice[i]);
            panel[i].add(goodsQuality[i]);
            panel[i].add(detail[i]);
            panel[i].add(contact[i]);
            panel[i].add(goodsBuy[i]);

            mainPanel.add(panel[i]);
        }
        this.add(mainPanel);

        this.pack();
    }


    public static void main(String[] args){
        MainPage_GUI trya = new MainPage_GUI();
        trya.showMain(-1);
    }


    private void addLogIn(){
        //crate log in and sell button
        JPanel logInPanel = new JPanel(new GridLayout(1,6));
        logInPanel.add(sell);
        JLabel block1 =new JLabel();
        logInPanel.add(block1);
        JLabel block2 =new JLabel();
        logInPanel.add(block2);
        JLabel block3 =new JLabel();
        logInPanel.add(block3);
        logInPanel.add(myChart);
        //sell.setHorizontalAlignment(JButton.LEFT);
        logInPanel.add(logIn);
        //logIn.setHorizontalAlignment(JButton.RIGHT);
        mainPanel.add(logInPanel);
    }

    private String logInOrNot(int userIndex){
        if(userIndex == -1){
            System.out.println(userIndex);
            System.out.println("Log In");
            return "Log In";
        }else{
            System.out.println(userIndex);
            System.out.println("lalala");
            return myOpr.getUserName(userIndex);
        }
    }

    public void uploadItems(String name, String price, int amount, BufferedImage image){
        myOpr.insertItem(name,price,amount, userIndex, image);
    }

    private void addSearchBar(){
        //crate search bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchBar);
        searchPanel.add(search);
        mainPanel.add(searchPanel);
    }

    private void addTittle(){
        //set title for products
        JLabel block0 = new JLabel();
        JLabel Name = new JLabel("Products Name");
        Name.setHorizontalAlignment(JLabel.LEFT);
        JLabel price = new JLabel("Price");
        price.setHorizontalAlignment(JLabel.LEFT);
        JLabel amount = new JLabel("Amount");
        amount.setHorizontalAlignment(JLabel.LEFT);
        JLabel block = new JLabel();
        JLabel block1 = new JLabel();
        JLabel block2 = new JLabel();
        JPanel title = new JPanel(new GridLayout(1,7));
        title.add(block0);
        title.add(Name);
        title.add(price);
        title.add(amount);
        title.add(block);
        title.add(block1);
        title.add(block2);
        mainPanel.add(title);
    }

    private BufferedImage setIcon(){
        try {
            return ImageIO.read(getClass().getResource("picture.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logIn){
            Login_GUI log = new Login_GUI();
            log.setVisible(true);
            log.setSize(400, 400);
            this.dispose();
            log.setResizable(false);

        }else if(e.getSource() == myChart){
            Login_GUI logInInfo = new Login_GUI();
            System.out.println("This is user ID in main" +userIndex);
            if(userIndex == -1){
                JOptionPane.showMessageDialog(null,"Please Log In");
            }else{
                ArrayList<Integer> items = myOpr.getCartItem(userIndex);
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> price = new ArrayList<>();
                ArrayList<Integer> amount = new ArrayList<>();
                ArrayList<String> time = new ArrayList<>();
                for (Integer item : items) {
                    name.add(myOpr.getItemName(item));
                    price.add(myOpr.getItemPrice(item));
                    amount.add(myOpr.getItemAmount(item));
                    time.add("Not set yet");
                }
                Cart_GUI cart_gui = new Cart_GUI(items.size(),name,price,amount,time,items);
                cart_gui.setSize(600,400);
                cart_gui.setVisible(true);
                cart_gui.setResizable(false);
            }
        }else if ((e.getSource() == search)){
            int check = 0;
            mainPanel.removeAll();
            addLogIn();
            addSearchBar();
            addTittle();
            String keyWords = searchBar.getText();
            for(int i = 0; i < goodsNumber; i++){
                System.out.println(i);
                System.out.println(goodsName[i].getText() + " && " + keyWords);
                if (goodsName[i].getText().contains(keyWords)){
                    check++;
                    System.out.println("hahahhaa");
                    mainPanel.add(panel[i]);
                }
            }
            if (check == 0){
                JPanel panel = new JPanel(new FlowLayout());
                JLabel noGood = new JLabel("No Matched Product Founded ");
                panel.add(noGood);
                mainPanel.add(panel);
            }
            mainPanel.updateUI();
            //repaint();
        }else if(e.getSource() == sell){
            Selling_GUI sell = new Selling_GUI();
            sell.setSize(500,150);
            sell.setResizable(true);
            sell.setVisible(true);
        }else{
            for (int i = 0; i < goodsNumber; i++){
                if(e.getSource() == goodsBuy[i]){
                    myOpr.addToChart(userIndex,i);
                    JOptionPane.showMessageDialog(null, "item" + i + " was added to chart");
                }else if(e.getSource() == contact[i]){
                    JOptionPane.showMessageDialog(null,"Contact " + i + " Server");
                }else if (e.getSource() == detail[i]){
                    JOptionPane.showMessageDialog(null, "Detail of " + i + " does not exist" );
                }

            }
        }
    }
}
