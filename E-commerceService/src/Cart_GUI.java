import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JCheckBox;


public class Cart_GUI extends JFrame {
    private JPanel mainPanel;
    private int goodsNumber;
    private JButton markAll;
    private JButton remove;
    private JCheckBox[] goodsBuy;
    private JLabel[] goodsName;
    private JLabel[] goodsQuality;
    private JPanel[] panel;
    private JLabel[] goodsPrice;
    private JLabel total;
    private JLabel totalAmount;
    private boolean check;
    private BigDecimal totalPrice;
    private ArrayList<Integer> removeNum;
    private JPanel totalPanel;
    private JButton buy;


    //public Cart_GUI(int goodsNumber, ArrayList<String> name, ArrayList<String> price, ArrayList<Integer> amout) {
    public Cart_GUI(int goodsNumber) {
        super("Cart");
        this.goodsNumber = goodsNumber;
        mainPanel = new JPanel(new GridLayout(this.goodsNumber + 2, 1, 1, 6));
        panel = new JPanel[this.goodsNumber];
        goodsBuy = new JCheckBox[this.goodsNumber];
        goodsName = new JLabel[this.goodsNumber];
        goodsQuality = new JLabel[this.goodsNumber];
        goodsPrice = new JLabel[this.goodsNumber];
        total = new JLabel("Total : ");
        totalAmount = new JLabel();
        remove = new JButton("Remove");
        markAll = new JButton("Mark/Un-mark All");
        check = true;
        totalPrice = new BigDecimal("0");
        removeNum = new ArrayList<>();
        buy = new JButton("Buy");


        setTitle();

        CheckAll action = new CheckAll();
        markAll.addActionListener(action);

        GetHandler removeHandler = new GetHandler();
        remove.addActionListener(removeHandler);
        buy.addActionListener(removeHandler);

        for (int i = 0; i < this.goodsNumber; i++) {
            //setItempanel(name.get(i),price.get(i),amout.get(i),i);
            setItempanel("name", "100", 10, i);
        }



        totalAmount.setText(totalPrice.toString());

        totalPanel = new JPanel();
        setTotalPanel();

        add(mainPanel);
        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroll);


    }

    private void setTitle(){
        JLabel Name = new JLabel("Products Name");
        Name.setHorizontalAlignment(JLabel.LEFT);
        JLabel price = new JLabel("Price");
        price.setHorizontalAlignment(JLabel.LEFT);
        JLabel amount = new JLabel("Amount");
        amount.setHorizontalAlignment(JLabel.LEFT);
        JLabel mark = new JLabel("Mark");
        amount.setHorizontalAlignment(JLabel.LEFT);
        JPanel title = new JPanel(new GridLayout(1, 4));
        title.add(Name);
        title.add(price);
        title.add(amount);
        title.add(mark);
        mainPanel.add(title);
    }

    public void setItempanel(String itemname, String itemprice, int amount, int i) {
        goodsName[i] = new JLabel(itemname);
        goodsPrice[i] = new JLabel(itemprice);
        goodsQuality[i] = new JLabel("" + amount);
        goodsBuy[i] = new JCheckBox("", true);
        CheckBoxHandler handler = new CheckBoxHandler();
        goodsBuy[i].addItemListener(handler);

        panel[i] = new JPanel(new GridLayout(1, 4));

        if (goodsBuy[i].isSelected()) {
            BigDecimal ppp = new BigDecimal(goodsPrice[i].getText());
            totalPrice = totalPrice.add(ppp);
        }

        panel[i].add(goodsName[i]);
        panel[i].add(goodsPrice[i]);
        panel[i].add(goodsQuality[i]);
        panel[i].add(goodsBuy[i]);
        mainPanel.add(panel[i]);
    }

    private class CheckBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            check = true;
            totalPrice = BigDecimal.ZERO;
            for (int i = 0; i < goodsNumber; i++) {
                if (goodsBuy[i].isSelected()) {
                    BigDecimal ppp = new BigDecimal(goodsPrice[i].getText());
                    totalPrice = totalPrice.add(ppp);
                } else {
                    check = false;
                }
            }
            totalAmount.setText(totalPrice.toString());
        }
    }

    private class CheckAll implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (check) {
                for (int i = 0; i < goodsNumber; i++) {
                    goodsBuy[i].setSelected(false);
                }
            } else {
                for (int i = 0; i < goodsNumber; i++) {
                    goodsBuy[i].setSelected(true);
                }
            }
        }
    }

    private class GetHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < goodsNumber; i++) {
                if (!goodsBuy[i].isSelected()) {
                    removeNum.add(i);
                }
        }
        System.out.print(removeNum);
    }
    }

    public void setTotalPanel(){
        totalAmount.setText(totalPrice.toString());
        totalPanel.add(total);
        totalPanel.add(totalAmount);
        totalPanel.add(markAll);
        totalPanel.add(remove);
        totalPanel.add(buy);
        mainPanel.add(totalPanel);
    }

    public ArrayList<Integer> getRemoveNum(){
        return removeNum;
    }
/*public static void main(String[] args){
        Cart_GUI cart_gui = new Cart_GUI(50);
        cart_gui.setSize(600,400);
        cart_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart_gui.setResizable(true);
        cart_gui.setVisible(true);
    }*/
}

