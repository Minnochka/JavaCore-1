package lesson6.Server;

import java.sql.*;
import java.util.HashMap;

import static javax.swing.UIManager.getString;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUsernameByLoginPass(String login, String password){
        String sqlGetUsername = String.format("select username, id from users u where u.login = '%s' and u.password = '%s' and status = 0", login, password);
        try {
            ResultSet rs = stmt.executeQuery(sqlGetUsername);
            if(rs.next()){
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int userAuthorized(String login, String password){
        String sqlAuthorized = String.format("update users set status = 1 where login = '%s' and password = '%s'", login, password);
        int upd = 0;
        System.out.println("upd=" + upd);
        try {
            upd = stmt.executeUpdate(sqlAuthorized);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("upd2=" + upd);
        return upd;
    }

    public static int userClosed(String username){
        String sqlAuthorized = String.format("update users set status = 0 where username = '%s'", username);
        int upd = 0;
        try {
            upd = stmt.executeUpdate(sqlAuthorized);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public static HashMap chatList(String username){
        String sqlGetUsername = String.format("select username, id from users u where username != '%s' ", username);
        HashMap<Integer, String> res = new HashMap<Integer, String>();
        try {
            ResultSet rs = stmt.executeQuery(sqlGetUsername);
            res.put(0,"Bce");
            while(rs.next()){
                res.put(rs.getInt("id"), rs.getString("username")) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
