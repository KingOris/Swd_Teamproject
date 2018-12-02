

import javax.swing.*;


public class OrderHistory_GUI extends Cart_GUI {
    public OrderHistory_GUI(int goodsNumber) {
        super(goodsNumber);
        setTitle("Order History");
        removeBuy();
    }

    public static void main(String[] args){
        OrderHistory_GUI cart_gui = new OrderHistory_GUI(10);
        cart_gui.setSize(600,400);
        cart_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart_gui.setResizable(true);
        cart_gui.setVisible(true);
    }
}