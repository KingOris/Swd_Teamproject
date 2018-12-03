import Database.DataBase_Con;
import Database.DataBase_op;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class OrderHistory_GUI extends Cart_GUI {
    private int goodsNumber;
    private DataBase_Con myDB = new DataBase_Con();
    private DataBase_op myOpr=new DataBase_op(myDB);
    public OrderHistory_GUI(int goodsNumber, ArrayList<String> name, ArrayList<String> price, ArrayList<Integer> amout, ArrayList<String> time, ArrayList<Integer> itemId) {
        super(goodsNumber,name, price, amout, time, itemId);
        this.goodsNumber = goodsNumber;
        setTitle("Order History");
        removeBuy();
        for(ActionListener actionListener:remove.getActionListeners()){
            remove.removeActionListener(actionListener);
        }
        GetHandler removeHandler = new GetHandler();
        remove.addActionListener(removeHandler);
    }

    private class GetHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Integer> removeNum = new ArrayList<>();
            for (int i = 0; i < goodsNumber; i++) {
                if (goodsBuy[i].isSelected()) {
                    goodsBuy[i].setSelected(false);
                    mainPanel.remove(panel[i]);
                    removeNum.add(itemId.get(i));
                }
            }
            myOpr.removeItemFormHistory(removeNum);
            mainPanel.updateUI();
        }
    }
}