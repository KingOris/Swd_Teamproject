package Database;

import com.sun.codemodel.internal.JOp;
import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataBase_op {
    private DataBase_Con myDB=null;
    private Connection conn=null;
    private Statement stmt=null;
    private int count = 0;
    private int count1 = 0;
    private String id;
    private String password;
    private String Id;
    private String userP;
    private String emailA;
    private String pho;
    private String user_t;
    public DataBase_op(DataBase_Con myDB){
        conn=myDB.getMyConnection();
        stmt=myDB.getMyStatement();
    }
    public boolean  selectPassword(String mpassword){
        String sql="SELECT * FROM user_information";
        try{
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                password=rs.getString("user_password");
                count++;
                //System.out.print(rs.getString("password")+"  ");
                if(password.equals(mpassword)&&(count==count1)){
                    //System.out.print("number2:"+number2);
                    return true;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean selectName(String mname){
        String sql="SELECT * FROM user_information";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                id=rs.getString("userId");
                count1++;
                if(id.equals(mname)){
                    //System.out.print("number1:"+number1);
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void insertData(String Idn, String userPo, String emailAa, String phon, String user_te){
        try{
            String newType1=new String(Idn.getBytes());
            String newType2=new String(userPo.getBytes());
            String newType3=new String(emailAa.getBytes());
            String newType4=new String(phon.getBytes());
            String newType5=new String(user_te.getBytes());

            String sql = "INSERT INTO user_information(userId,user_password,emailAdd,phone ,user_type)VALUES("+"'" + newType1+ "'"+",'"+newType2+"','"+newType3+"','"+newType4+"','"+newType5+"')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }

    public void removeItem(ArrayList<Integer> index){
        String cmd = "";
        for(int i = 0; i < index.size(); i++){
            cmd = "DELETE FROM cart WHERE itemid ="+"'"+ index.get(i) + "'";
            System.out.println(cmd);
            try {
                stmt.executeUpdate(cmd);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void removeItemFormHistory(ArrayList<Integer> index){
        for(int i = 0; i < index.size(); i++){
            try {
                stmt.executeUpdate("DELETE FROM buy_history WHERE item_id ="+"'"+ index.get(i) + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addItem(String itemName, int price, int amount, int sellerID, Blob image){
        String name = new String(itemName.getBytes());
        String cmd = "INSERT INTO item(item_name,item_amount,item_price,item_seller ,item_image)VALUES("+"'" + name+ "'"+",'"+price+"','"+amount+"','"+sellerID+"','"+image+"')";
        System.out.println(cmd);
        try {
            stmt.executeQuery(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToChart(int userID, int itemID, int amount){
        itemID = itemID+1;
        int oldAmount;
        int totalAmount;
        boolean needUpdate = true;



        try {
            String cmd2 = "SELECT itemId FROM cart WHERE buyerId = ("+"'"+userID+"'"+")";
            ResultSet rs = stmt.executeQuery(cmd2);
            System.out.println("This is cmd 2"+cmd2);
            while(rs.next()){
                System.out.println("This is rs.itemId" +rs.getString("itemId"));
                if(itemID == rs.getInt("itemId")){
                    System.out.println("This is itemID and rs.itemID " + itemID + rs.getString("itemId"));
                    String cmd3 = "SELECT item_amount FROM cart WHERE itemId = ("+"'" +itemID+"'"+")";
                    ResultSet rs1 = stmt.executeQuery(cmd3);
                    System.out.println("This is cmd 3" + cmd3);
                    if(rs1.next()){
                        oldAmount = rs1.getInt("item_amount");
                        System.out.println("oldAmount Update to "+ rs1.getInt("item_amount"));
                        totalAmount = amount + oldAmount;
                        System.out.println("Total amount is " + totalAmount);
                        String cmd4 = "UPDATE cart SET item_amount = ("+"'"+totalAmount+"'"+") WHERE itemId = ("+"'"+itemID+"'"+")" ;
                        stmt.executeUpdate(cmd4);
                        System.out.println("cmd 4 is " + cmd4);
                        needUpdate = false;
                        System.out.println("boolean updated " + needUpdate);
                    }
                }
            }
            if(needUpdate){
                String cmd = "INSERT INTO cart(buyerId,itemId,item_amount)VALUES("+"'" + userID+ "'"+",'"+itemID+"', "+"'" + amount+"'"+")";
                stmt.executeUpdate(cmd);
                System.out.println("This is cmd " + cmd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getCartItem(int userID){
        System.out.println("Get Cart Item of " + userID);
        String cmd = "SELECT itemId FROM cart WHERE buyerId = ("+"'" + userID + "'"+ ")";
        ArrayList<Integer> itemID = new ArrayList<>();
        int j = 0;
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            while(rs.next()){
                j++;
                itemID.add(rs.getInt("itemId"));
                System.out.println("This is " + j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < itemID.size(); i++){
            System.out.println("This is items " + itemID.get(i));
        }
        return itemID;
    }

    public int getItemId(String itemName){
        String name = new String(itemName.getBytes());
        String cmd = "SELECT item_id FROM item WHERE item_name = ("+"'" + name + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            return rs.getInt("item_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getItemName(int index){
        int ind = index+1;
        String cmd = "SELECT item_name FROM item WHERE item_id = ("+"'" + ind + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                System.out.println("Get name of item");
                return rs.getString("item_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NO Name";
    }

    public String getItemPrice(int index){
        int ind = index+1;
        String cmd = "SELECT item_price FROM item WHERE item_id = ("+"'" + ind + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                return rs.getString("item_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public int getItemAmount(int index){
        int ind = index+1;
        String cmd = "SELECT item_amount FROM item WHERE item_id = ("+"'" + ind + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                return rs.getInt("item_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getItemAmountInCart(int index){
        //int ind = index +1;
        //String cmds = "SELECT item_amount FROM item WHERE itemId =("+"'" + ind+"'"+")";
        try {
            ResultSet rs = stmt.executeQuery("SELECT item_amount FROM cart WHERE itemId =("+"'" + index+"'"+")");
            if(rs.next()){
                return rs.getInt("item_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getItemSellerId(int index){
        String cmd = "SELECT item_seller FROM item WHERE item_id = ("+"'" + index + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                return rs.getInt("item_seller");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public byte[] getImage(int goodID){
        int goodID2 = goodID+1;
        String cmd = "SELECT item_image FROM item WHERE item_id = ("+"'" + goodID2 + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                System.out.println("Image opening " + rs.getBytes("item_image"));
                return rs.getBytes("item_image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setNumber1(){
        count=0;
    }

    public void setNumber2(){
        count=0;
    }

    public String getUserName(int userInd){
        String name = "SELECT userId FROM user_information WHERE user_index = ("+"'" + userInd + "'"+ ")";
        try {
            System.out.println("Trying");
            ResultSet rs = stmt.executeQuery(name);
            if(rs.next()) {
                return rs.getString("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return"Log In";
    }

    public int getUserIndex(String userId){

        try {
            String userName=new String(userId.getBytes());
            String name = "SELECT user_Index FROM user_information WHERE userId = ("+"'" + userName + "'"+ ")";
            ResultSet rs = stmt.executeQuery(name);
            //System.out.println("This is what in DB "+ rs.getInt("user_index"));
            if(rs.next()) {
                System.out.println("This is what in DB "+ rs.getInt("user_index"));
                return rs.getInt("user_index");
            }
        } catch (SQLException e) {
            return -1;
        }
        return -1;
    }

    public void insertItem(String itemName, String itemPrice, int itemAmount, int sellerId, BufferedImage image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String name = new String(itemName.getBytes());
        String price = new String(itemPrice.getBytes());
        try {
            ImageIO.write(image,"jpg", byteArrayOutputStream);
            byte[] imageByte = byteArrayOutputStream.toByteArray();
            try {
                Blob blob = conn.createBlob();
                blob.setBytes(1,imageByte);
                String cmd = "INSERT INTO item(item_name, item_amount, item_price, item_seller, item_image)" +
                        "VALUES("+"'" + name+ "'"+","+"'"+itemAmount+"'" +", "+"'" +price+"'"+","+"'" + sellerId + "'"+", "+"'" +blob+"'"+")";
                stmt.executeUpdate(cmd);
                //stmt.executeQuery(cmd);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Products Message Not Complete");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Products Message Not Complete");
        }
    }

    public int getGoodNum(){
        int totalNum = 0;
        String cmd = "SELECT item_id FROM item";
        try {
            ResultSet st = stmt.executeQuery(cmd);
            while(st.next()){
                totalNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalNum;
    }

    public int couldSell(int index){
        int index2 = index +1;
        String cmd = "SELECT user_type FROM user_information WHERE user_index = ("+"'" + index+"'"+")";
        System.out.println("========================"+cmd);
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            System.out.println("========================"+rs.next());
            System.out.println("=================="+rs.getString("user_type"));
            switch (rs.getString("user_type")) {
                case "Seller":
                    System.out.println("Seller");
                    return 1;
                case "Buyer":
                    System.out.println("Buyer");
                    return 2;
                case "Seller and Buyer":
                    System.out.println("Both");
                    return 3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateItemAmount(int itemId, int itemAmount){
        int restAmount = 0;
        System.out.println("This is item Amount "+ itemAmount);
        String cmd = "SELECT item_amount FROM item WHERE item_id = ("+"'"+itemId +"'"+")";
        System.out.println(cmd);
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                int totalAmount = rs.getInt("item_amount");
                restAmount = totalAmount - itemAmount;
                if(restAmount<0){
                    JOptionPane.showMessageDialog(null,"This product dos not have that much amount.");
                    return;
                }
                System.out.println("This is what we rest " + restAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String cmd1 = "UPDATE item SET item_amount = ("+"'"+restAmount+"'"+") WHERE item_id = ("+"'"+itemId+"'"+")";
        System.out.println(cmd1);
        try {
            stmt.executeUpdate(cmd1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------");
    }

    public void addToPurchaseHistory(int user_id, int item_id, int amount, String date){
        int itemId = item_id+1;
        String nowDate = new String(date.getBytes());
        String cmd = "INSERT INTO buy_history(user_id, item_id, amount, sell_data)VALUES("+"'"+user_id+"'"+","+"'"+itemId+"'"+","+"'"+amount+"'"+","+"'"+nowDate+"'"+")";
        try {
            stmt.executeUpdate(cmd);
            System.out.println(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getItemIdForHistory(int index){
        ArrayList<Integer> itemId = new ArrayList<>();
        String cmd = "SELECT item_id FROM buy_history WHERE user_id = ("+"'"+index+"'"+")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            while(rs.next()){
                itemId.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemId;
    }

    public int getAmountFromHistory(int index){
        int index2 = index +1;
        String cmd = "SELECT amount FROM buy_history WHERE item_id = ("+"'"+index+"'"+")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                return rs.getInt("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getDateFromHistory(int index){
        String cmd = "SELECT sell_data FROM buy_history WHERE item_id + ("+"'"+index+"'"+")";
        try {
            ResultSet rs = stmt.executeQuery(cmd);
            if(rs.next()){
                return rs.getString("sell_data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Time Lost";
    }






    //public
}
