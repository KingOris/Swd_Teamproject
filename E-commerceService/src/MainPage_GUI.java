import javax.swing.*;
import java.awt.*;

public class MainPage_GUI extends JFrame{
    String log = "Log In";
    private JButton logButton = new JButton("Log In");
    private int goodsNumber = 100;
    private JPanel mainPanel;

    // crate products detail panel
    private JLabel[] goodsName = new JLabel[goodsNumber];
    private JLabel[] goodsPrice = new JLabel[goodsNumber];
    private JLabel[] goodsQuality = new JLabel[goodsNumber];
    private JButton[] goodsBuy =  new JButton[goodsNumber];
    private JPanel[] panel = new JPanel[goodsNumber];

    private JTextField searchBar = new JTextField(20);
    private JButton search = new JButton("Search");

    private JButton logIn = new JButton(log);
    private JButton sell = new JButton("sell");

    private MainPage_GUI(){
        super("Main Page");
        mainPanel = new JPanel(new GridLayout(goodsNumber+3,1));
        initialMainPage(goodsNumber);
        JScrollPane scroll = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        this.setVisible(true);
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initialMainPage(int n){
        //crate log in and sell button
        JPanel logInPanel = new JPanel(new GridLayout(1,6));
        logInPanel.add(sell);
        JLabel block1 =new JLabel();
        logInPanel.add(block1);
        JLabel block2 =new JLabel();
        logInPanel.add(block2);
        JLabel block3 =new JLabel();
        logInPanel.add(block3);
        JLabel block4 =new JLabel();
        logInPanel.add(block4);
        //sell.setHorizontalAlignment(JButton.LEFT);
        logInPanel.add(logIn);
        //logIn.setHorizontalAlignment(JButton.RIGHT);
        mainPanel.add(logInPanel);


        //crate search bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchBar);
        searchPanel.add(search);
        mainPanel.add(searchPanel);


        //set title for products
        JLabel Name = new JLabel("Products Name");
        JLabel price = new JLabel("Price");
        JLabel amount = new JLabel("Amount");
        JLabel block = new JLabel();
        JPanel title = new JPanel(new GridLayout(1,4));
        title.add(Name);
        title.add(price);
        title.add(amount);
        title.add(block);
        mainPanel.add(title);
        for(int i =0; i< n; i++){
            goodsName[i] = new JLabel("This is " + i + "'st " + "product");
            goodsPrice[i]= new JLabel("The price is " + i);
            goodsQuality[i] = new JLabel("The quality is" + i);
            goodsBuy[i] = new JButton("Buy");
            //goodsBuy[i].setPreferredSize(new Dimension(3,2));


            panel[i] = new JPanel();
            panel[i].setLayout((new GridLayout(1,4)));
            panel[i].add(goodsName[i]);
            panel[i].add(goodsPrice[i]);
            panel[i].add(goodsQuality[i]);
            panel[i].add(goodsBuy[i]);

            mainPanel.add(panel[i]);
        }
        this.add(mainPanel);

        this.pack();
    }


    public static void main(String[] args){
        new MainPage_GUI();
    }

}
