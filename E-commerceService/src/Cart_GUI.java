import Database.DataBase_Con;
import Database.DataBase_op;

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
    private JLabel[] time;
    private JPanel all;
    private DataBase_Con myDB = new DataBase_Con();
    private DataBase_op myOpr=new DataBase_op(myDB);
    private ArrayList<Integer> itemId;



    public Cart_GUI(int goodsNumber, ArrayList<String> name, ArrayList<String> price, ArrayList<Integer> amout, ArrayList<String> time, ArrayList<Integer> itemId) {
    //public Cart_GUI(int goodsNumber) {
        super();
        setTitle("Cart");

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
        this.time = new JLabel[this.goodsNumber];
        all = new JPanel(new GridLayout(3,1,10,10));
        this.itemId = itemId;
        setTitle();

        CheckAll action = new CheckAll();
        markAll.addActionListener(action);

        GetHandler removeHandler = new GetHandler();
        PurchaseHandler purchase = new PurchaseHandler();
        remove.addActionListener(removeHandler);
        buy.addActionListener(purchase);

        for (int i = 0; i < goodsNumber; i++) {
            setItempanel(name.get(i),price.get(i),amout.get(i),i, time.get(i));
            //System.out.println("This is item" + items.get(i));
            //setItempanel(myOpr.getItemName(items.get(i)), myOpr.getItemPrice(items.get(i)), myOpr.getItemAmount(items.get(i)), i,"1996-12-13");
        }

        totalAmount.setText(totalPrice.toString());

        totalPanel = new JPanel();
        all.add(mainPanel);
        setTotalPanel();
        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        all.add(scroll);
        all.add(totalPanel);add(all);
    }

    private void setTitle(){
        JLabel Name = new JLabel("Products Name");
        Name.setHorizontalAlignment(JLabel.LEFT);
        JLabel price = new JLabel("Price");
        price.setHorizontalAlignment(JLabel.LEFT);
        JLabel amount = new JLabel("Amount");
        amount.setHorizontalAlignment(JLabel.LEFT);
        JLabel mark = new JLabel("Mark");
        mark.setHorizontalAlignment(JLabel.LEFT);
        JLabel date = new JLabel("Date");
        date.setHorizontalAlignment(JLabel.LEFT);
        JPanel title = new JPanel(new GridLayout(1, 5));
        title.add(Name);
        title.add(price);
        title.add(amount);
        title.add(mark);
        title.add(date);
        all.add(title);
    }

    public void setItempanel(String itemname, String itemprice, int amount, int i, String tim) {
        goodsName[i] = new JLabel(itemname);
        goodsPrice[i] = new JLabel(itemprice);
        goodsQuality[i] = new JLabel("" + amount);
        goodsBuy[i] = new JCheckBox("", true);
        CheckBoxHandler handler = new CheckBoxHandler();
        goodsBuy[i].addItemListener(handler);
        time[i] = new JLabel(tim);

        panel[i] = new JPanel(new GridLayout(1, 5));

        if (goodsBuy[i].isSelected()) {
            BigDecimal ppp = new BigDecimal(goodsPrice[i].getText());
            totalPrice = totalPrice.add(ppp);
        }

        panel[i].add(goodsName[i]);
        panel[i].add(goodsPrice[i]);
        panel[i].add(goodsQuality[i]);
        panel[i].add(goodsBuy[i]);
        panel[i].add(time[i]);
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
                if (goodsBuy[i].isSelected()) {
                    goodsBuy[i].setSelected(false);
                    mainPanel.remove(panel[i]);
                    removeNum.add(itemId.get(i));
                }
        }
        myOpr.removeItem(removeNum);
        mainPanel.updateUI();
    }
    }

    private class PurchaseHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Integer> amount = new ArrayList<>();

            for (int i = 0; i < goodsNumber; i++) {
                if (goodsBuy[i].isSelected()) {
                    goodsBuy[i].setSelected(false);
                    mainPanel.remove(panel[i]);
                    removeNum.add(itemId.get(i));
                    amount.add(Integer.parseInt(goodsQuality[i].getText()));
                }
            }
            for (int j = 0; j<removeNum.size(); j++) {
                myOpr.updateItemAmount(removeNum.get(j),amount.get(j));
            }
            myOpr.removeItem(removeNum);
            removeNum.clear();
            amount.clear();
            mainPanel.updateUI();
            System.out.println("==================================================");
            JOptionPane.showMessageDialog(null,"Item purchased, Go Order History to View What You Brought");
        }
    }



    public void setTotalPanel(){
        totalAmount.setText(totalPrice.toString());
        totalPanel.add(total);
        totalPanel.add(totalAmount);
        totalPanel.add(markAll);
        totalPanel.add(remove);
        totalPanel.add(buy);
    }

    public void removeBuy(){
        totalPanel.remove(buy);
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

