package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase_op {
    private DataBase_Con myDB=null;
    private Connection conn=null;
    private Statement stmt=null;
    private int count = 0;
    private int count1 = 0;
    private String id;
    private String password;
    public DataBase_op(DataBase_Con myDB){
        conn=myDB.getMyConnection();//取得对象
        stmt=myDB.getMyStatement();//取得sql语句
    }
    public boolean  selectPassword(String mpassword){//查询密码
        String sql="SELECT * FROM user_information";
        try{
            ResultSet rs=stmt.executeQuery(sql);//返回结果集
            while(rs.next()){//指针向后移动
                password=rs.getString("password");
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
        String sql="SELECT scores,name,password FROM player";
        try{
            ResultSet rs=stmt.executeQuery(sql);//返回结果集
            while(rs.next()){//指针向后移动
                id=rs.getString("name");
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
    public void setNumber1(){
        count=0;
    }
    public void setNumber2(){
        count=0;
    }
}
