import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase_Con {
    private String DBDriver;
    private String DBURL;
    private String DBUser;
    private String DBPass;
    private Connection conn=null;
    private Statement stmt=null;
    public DataBase_Con(){
        DBDriver="com.mysql.jdbc.Driver";
        DBURL="jdbc:mysql://localhost:3306/info";
        DBUser="root";
        DBPass="Zxb960922.";
        try{
            Class.forName(DBDriver);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            conn= DriverManager.getConnection(DBURL,DBUser,DBPass);
            stmt=conn.createStatement();
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
