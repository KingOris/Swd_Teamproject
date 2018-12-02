import javax.swing.*;

public class Seller_GUI extends Cart_GUI {
    public Seller_GUI(int goodsNumber) {
        super(goodsNumber);
        setTitle("Seller");
        removeBuy();
    }

    public static void main(String[] args){
        Seller_GUI cart_gui = new Seller_GUI(10);
        cart_gui.setSize(600,400);
        cart_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart_gui.setResizable(true);
        cart_gui.setVisible(true);
    }
}
