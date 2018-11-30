import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import javax.swing.JCheckBox;


public class Cart_GUI extends JFrame {
    private JPanel mainPanel;
    private JPanel[] itemPanel;
    private int goodsNumber;
    private JCheckBox[] goodsBuy;
    private JLabel[] goodsName;
    private JLabel[] goodsQuality;
    private JPanel[] panel;
    private JLabel[] goodsPrice;
    private JLabel total;
    private JLabel totalAmount;

    public Cart_GUI(int goodsNumber) {
        super("Cart");
        this.goodsNumber = goodsNumber;
        mainPanel = new JPanel(new GridLayout(this.goodsNumber + 1, 1, 1, 6));
        itemPanel = new JPanel[this.goodsNumber];
        panel = new JPanel[this.goodsNumber ];
        goodsBuy = new JCheckBox[this.goodsNumber];
        goodsName = new JLabel[this.goodsNumber];
        goodsQuality = new JLabel[this.goodsNumber];
        goodsPrice = new JLabel[this.goodsNumber];
        total = new JLabel("Total : ");
        totalAmount = new JLabel();
        for(int i = 0; i<this.goodsNumber; i++){
            setItempanel("name","10",10,i);
        }

        JPanel totalPanel = new JPanel();
        totalPanel.add(total);
        totalPanel.add(totalAmount);

        mainPanel.add(totalPanel);
        add(mainPanel);
    }


    public void setItempanel(String itemname, String itemprice, int amount, int i) {
        goodsName[i] = new JLabel( itemname);
        goodsPrice[i]= new JLabel( itemprice);
        goodsQuality[i]= new JLabel("" + amount);
        goodsBuy[i] = new JCheckBox("",true);
        CheckBoxHandler handler = new CheckBoxHandler();
        goodsBuy[i].addItemListener(handler);

        panel[i] = new JPanel();

        panel[i].add(goodsName[i]);
        panel[i].add(goodsPrice[i]);
        panel[i].add(goodsQuality[i]);
        panel[i].add(goodsBuy[i]);
        mainPanel.add(panel[i]);
    }

    private class CheckBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            BigDecimal totalPrice = new BigDecimal("0");
            for (int i = 0; i < goodsNumber; i++) {
                if (goodsBuy[i].isSelected()) {
                    BigDecimal ppp = new BigDecimal(goodsPrice[i].getText());
                    totalPrice = totalPrice.add(ppp);
                    totalAmount.setText(totalPrice.toString());
                }
            }
        }
    }

    public static void main(String[] args){
        Cart_GUI cart_gui = new Cart_GUI(10);
        cart_gui.setSize(600,400);
        cart_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart_gui.setResizable(true);
        cart_gui.setVisible(true);
    }
}

