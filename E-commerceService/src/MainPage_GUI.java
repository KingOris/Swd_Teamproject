import javax.swing.*;
import java.awt.*;

public class MainPage_GUI extends JFrame{
    private JButton logButton = new JButton("Log In");
    private int goodsNumber = 10;
    private JPanel mainPanel;
    public MainPage_GUI(){
        super("Main Page");
        mainPanel = new JPanel(new GridLayout(goodsNumber,1));
        initialMainPage(goodsNumber);
        JScrollPane scrol = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrol);
        this.setVisible(true);
        this.setSize(400,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initialMainPage(int n){
        JLabel[] goodsName = new JLabel[n];
        JLabel[] goodsPrice = new JLabel[n];
        JLabel[] goodsQuality = new JLabel[n];
        JButton[] goodsBuy =  new JButton[n];
        JPanel[] panel = new JPanel[n];
        for(int i =0; i< n; i++){
            goodsName[i] = new JLabel("This is " + i + "'st " + "product");
            goodsPrice[i]= new JLabel("The price is " + i);
            goodsQuality[i] = new JLabel("The quality is" + i);
            goodsBuy[i] = new JButton("Buy");


            panel[i] = new JPanel();
            panel[i].setLayout((new GridLayout(1,4)));
            panel[i].add(goodsName[i]);
            panel[i].add(goodsPrice[i]);
            panel[i].add(goodsQuality[i]);
            panel[i].add(goodsBuy[i]);

            mainPanel.add(panel[i]);
        }
        this.add(mainPanel);
    }

    public static void main(String[] args){
        new MainPage_GUI();
    }

}
