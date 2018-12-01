import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage_GUI extends JFrame implements ActionListener {
    String log = "Log In";
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


    private JTextField searchBar = new JTextField(20);
    private JButton search = new JButton("Search");


    private JButton logIn = new JButton(log);
    private JButton sell = new JButton("sell");
    private JButton myChart = new JButton("My Chart");



    MainPage_GUI(){
        super("Main Page");
        goodsNumber = 10;
        mainPanel = new JPanel(new GridLayout(goodsNumber+3,1));
        goodsName = new JLabel[goodsNumber];
        goodsPrice = new JLabel[goodsNumber];
        goodsQuality = new JLabel[goodsNumber];
        goodsBuy =  new JButton[goodsNumber];
        panel = new JPanel[goodsNumber];
        contact = new JButton[goodsNumber];
        detail = new JButton[goodsNumber];
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
        this.setSize(800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initialMainPage(int n){
        for(int i =0; i< n; i++){

            goodsName[i] = new JLabel("This is " + i + "'st " + "product");
            goodsName[i].setMaximumSize(new Dimension(100, 30));
            goodsPrice[i]= new JLabel("The price is " + i);
            goodsQuality[i] = new JLabel("The quality is" + i);
            detail[i] = new JButton("Detail");
            detail[i].setMaximumSize(new Dimension(10,20));

            detail[i].addActionListener(this);
            goodsBuy[i] = new JButton("Add To Chart");
            goodsBuy[i].addActionListener(this);
            contact[i] = new JButton("Contact Seller");
            contact[i].addActionListener(this);


            panel[i] = new JPanel();
            panel[i].setLayout((new GridLayout(1,4)));
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
        new MainPage_GUI();
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

    private void addSearchBar(){
        //crate search bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchBar);
        searchPanel.add(search);
        mainPanel.add(searchPanel);
    }

    private void addTittle(){
        //set title for products
        JLabel Name = new JLabel("Products Name");
        Name.setHorizontalAlignment(JLabel.LEFT);
        JLabel price = new JLabel("Price");
        price.setHorizontalAlignment(JLabel.LEFT);
        JLabel amount = new JLabel("Amount");
        amount.setHorizontalAlignment(JLabel.LEFT);
        JLabel block = new JLabel();
        JLabel block1 = new JLabel();
        JLabel block2 = new JLabel();
        JPanel title = new JPanel(new GridLayout(1,6));
        title.add(Name);
        title.add(price);
        title.add(amount);
        title.add(block);
        title.add(block1);
        title.add(block2);
        mainPanel.add(title);
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
            Cart_GUI cart_gui = new Cart_GUI(goodsNumber);
            cart_gui.setSize(600,400);
            cart_gui.setVisible(true);
            cart_gui.setResizable(false);
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

            }
        }
    }
}
