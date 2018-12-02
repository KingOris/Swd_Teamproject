package Database;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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
        conn=myDB.getMyConnection();//取得对象
        stmt=myDB.getMyStatement();//取得sql语句
    }
    public boolean  selectPassword(String mpassword){//查询密码
        String sql="SELECT * FROM user_information";
        try{
            ResultSet rs=stmt.executeQuery(sql);//返回结果集
            while(rs.next()){//指针向后移动
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
    public boolean selectName(String mname){//查询id
        String sql="SELECT * FROM user_information";
        try{
            ResultSet rs = stmt.executeQuery(sql);//返回结果集
            while(rs.next()){//指针向后移动
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
        String userName=new String(userId.getBytes());
        String name = "SELECT user_Index FROM user_information WHERE userId = ("+"'" + userName + "'"+ ")";
        try {
            ResultSet rs = stmt.executeQuery(name);
            //System.out.println("This is what in DB "+ rs.getInt("user_index"));
            if(rs.next()) {
                System.out.println("This is what in DB "+ rs.getInt("user_index"));
                return rs.getInt("user_index");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*public void addItem(String itemName, int price, int amount){
        String cmd = "INSERT INTO item(item_name,item_amount,emailAdd,phone ,user_type)VALUES("+"'" + newType1+ "'"+",'"+newType2+"','"+newType3+"','"+newType4+"','"+newType5+"')";
    }*/

    public Image getImage(int goodID){

        String cmd = "SELECT item_image FROM item WHERE item_id = ("+"'" + goodID + "'"+ ")";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(cmd);
            resultSet = statement.executeQuery();
            byte[] image = null;
            while(resultSet.next()){
                image = resultSet.getBytes("item_image");
            }
            System.out.println(Toolkit.getDefaultToolkit().createImage(image));
            return Toolkit.getDefaultToolkit().createImage(image);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //public
}
