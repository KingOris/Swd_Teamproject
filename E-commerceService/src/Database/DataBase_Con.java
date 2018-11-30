package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase_Con {
    private static Connection conn=null;
    private static Statement stmt=null;
    public DataBase_Con(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动程序加载成功");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software","root","831015");
            stmt = conn.createStatement();
            System.out.print("success");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Connection getMyConnection(){
        return conn;
    }
    public Statement getMyStatement(){
        return stmt;
    }
    public void closeMyConnection(){
        try{
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();

        }
    }
}
