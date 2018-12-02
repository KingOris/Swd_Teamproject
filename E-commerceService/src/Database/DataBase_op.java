package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String newType1=new String(Idn.getBytes(),"GBK");
            String newType2=new String(userPo.getBytes(),"GBK");
            String newType3=new String(emailAa.getBytes(),"GBK");
            String newType4=new String(phon.getBytes(),"GBK");
            String newType5=new String(user_te.getBytes(),"GBK");

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
        String name = "SELECT userId FROM user_information WHERE user_index = VALUES(userId)";
        try {
            System.out.println("Trying");
            ResultSet rs = stmt.executeQuery(name);
            System.out.println(rs);
            System.out.println("Has next");
            System.out.println(rs.getString("userId"));
            return rs.getString("userId");
            //System.out.println(rs.getString("userId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return"Log In";
    }
}
