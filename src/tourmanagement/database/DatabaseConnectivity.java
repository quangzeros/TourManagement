package tourmanagement.database;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tourmanagement.model.Tour;
import tourmanagement.model.User;
import tourmanagement.utils.HashPassword;

/**
 * @author Aritra Chatterjee
 * JDBC sample program
 */

public class DatabaseConnectivity {
    
    public DatabaseConnectivity() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
      try {
        String query0="CREATE TABLE IF NOT EXISTS USER ("+  // Initial query to create table if not already present in DB
                "id VARCHAR(50) NOT NULL," +
                "name VARCHAR(30) NOT NULL," +
                "role VARCHAR(30) NOT NULL," +
                "email VARCHAR(30) NOT NULL," +
                "password VARCHAR(200) NOT NULL,"+
                "PRIMARY KEY(id,email)"+
                ")";
                statement.executeUpdate(query0); //executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) command
        String query1="CREATE TABLE IF NOT EXISTS TOUR ("+  // Initial query to create table if not already present in DB
                "id VARCHAR(50) NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "type VARCHAR(30) NOT NULL," +
                "customerType VARCHAR(30) NOT NULL," +
                "startDay DATE NOT NULL,"+
                "price DOUBLE NOT NULL,"+
                "PRIMARY KEY(id)"+
                ")";
                statement.executeUpdate(query1); //executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) command
        String query2="CREATE TABLE IF NOT EXISTS CART ("+  // Initial query to create table if not already present in DB
                "user_id VARCHAR(50) NOT NULL," +
                "tour_id VARCHAR(50) NOT NULL," +
                "PRIMARY KEY(user_id,tour_id),"+
                "FOREIGN KEY (user_id) REFERENCES USER(id)," +
                "FOREIGN KEY (tour_id) REFERENCES TOUR(id)" +
                ")";
                statement.executeUpdate(query2);        
    } finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }  
    }
    
    // Xử lí user 
    // Kiểm tra email trên 
    public static boolean checkEmail(String inp) throws SQLException{
       String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
      try {
        String query2 = "SELECT * FROM USER WHERE email = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query2);
        preparedStatement.setString(1, inp);
        ResultSet result = preparedStatement.executeQuery(); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
         if (result.next()) { 
            String email = result.getString("email");
            System.out.println("Email - " + email);
            return true;
        } else {
            return false; // Không tìm thấy email trong cơ sở dữ liệu
        }


    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    //Thêm người dùng mới
    public static void insertUser(String id,String name, String role, String email, String password) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
           String query1 = "INSERT INTO USER VALUES (?, ?, ? ,? ,?)";
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, id);
            preStat.setString(2, name); //Using the setter methods to substitute values corresponding to the ?s
            preStat.setString(3, role);
            preStat.setString(4, email);
            preStat.setString(5, password);
            preStat.executeUpdate(); //Executing the statement using executeUpdate()

    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    //Kiểm tra tài khoản
    public static boolean checkAccount(String email, String inputPassword) throws SQLException, NoSuchAlgorithmException,InvalidKeySpecException{
         String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
      try {
        String query2 = "SELECT * FROM USER WHERE email = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query2);
        preparedStatement.setString(1, email);
        ResultSet result = preparedStatement.executeQuery(); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
         if (result.next()) { 
            String password = result.getString("password");
            return HashPassword.validatePassword(inputPassword , password);

        } else {
            return false; // Không tìm thấy email trong cơ sở dữ liệu
        }


    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    // Lấy thông tin người dùng
    public static User findUser(String ema) throws SQLException{
         String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
      try {
        String query2 = "SELECT * FROM USER WHERE email = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query2);
        preparedStatement.setString(1, ema);
        ResultSet result = preparedStatement.executeQuery(); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
         if (result.next()) {
            String id = result.getString("id");
            String email = result.getString("email");
            String name = result.getString("name");
            String role = result.getString("role");
            return new User(id,role,email,name);      
        } else {
            return null;
        }


    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    
    // Xử lí Tour
    //Lấy các Tour trong cơ sở dữ liệu
    public static List<Tour> getTours() throws SQLException{
         String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
             String query2 = "SELECT * FROM TOUR ORDER BY price;";
             ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
             List<Tour> listTour = new ArrayList<>();
        while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
            String id = result.getString("id"); //Getters extract corresponding data from column names
            String name = result.getString("name");
            String type = result.getString("type");
            String customerType = result.getString("customerType");
            Date startDay = result.getDate("startDay");
            double price = result.getDouble("price");
            
            listTour.add(new Tour(id,name,type,customerType,startDay,price));
           
        }
            return listTour;
    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }  
    }
    //Thêm Tour vào Db    //Thêm Tour vào Db
    public static void addTour(String id, String name, String type, String customerType, String startDay, Double price) throws SQLException{
          String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
           String query1 = "INSERT INTO TOUR VALUES (?, ?, ? ,? ,? ,?)";
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, id);
            preStat.setString(2, name); //Using the setter methods to substitute values corresponding to the ?s
            preStat.setString(3, type);
            preStat.setString(4, customerType);
            preStat.setString(5, startDay);
            preStat.setDouble(6, price);
            preStat.executeUpdate(); //Executing the statement using executeUpdate()

    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    // Sửa thông tin Tour
    public static boolean updateTour(String id, String name, String type, String customerType, String startDay, Double price) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
     try {
           String sql = "UPDATE TOUR SET name = ?, type = ?, customerType = ?, startDay = ?, price = ?  WHERE id = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(sql);
            // Thay your_table bằng tên bảng của bạn, column1, column2 là tên cột và id là khóa chính

            // Thay đổi giá trị của các cột
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, customerType);
            preparedStatement.setString(4, startDay);
            preparedStatement.setDouble(5, price);
            preparedStatement.setString(6, id); // Điều kiện WHERE

            // Thực thi câu lệnh UPDATE
            int rowsAffected = preparedStatement.executeUpdate();
            
            return rowsAffected == 1 ? TRUE : FALSE;
            
    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    // Xóa Tour
    public static boolean deleteTour(String id) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
           String query1 = "DELETE FROM TOUR WHERE id = ?;";
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, id);
            int rowsAffected = preStat.executeUpdate();; //Executing the statement using executeUpdate()
            return rowsAffected == 1  ? TRUE : FALSE;
    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }

    
    //AddToCart
    public static void addToCart(String user_id, String tour_id) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
             String query1 = "INSERT INTO CART VALUES (?, ?)";
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, user_id);
            preStat.setString(2, tour_id); //Using the setter methods to substitute values corresponding to the ?s
 
            preStat.executeUpdate(); //Executing the statement using executeUpdate()

    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
    }
    
    //GetCartFromUser
    public static List<Tour> getCartOfUser(String user_id) throws SQLException{
         String url = "jdbc:mysql://localhost:3306/tourmanagement"; //URL of database to be connected
       Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
       Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
       
       
     try {
             String query2 = "SELECT * FROM tour WHERE id IN (SELECT tour_id FROM cart WHERE user_id = ? );";
             PreparedStatement preparedStatement = myConn.prepareStatement(query2);
            preparedStatement.setString(1, user_id);
            ResultSet result = preparedStatement.executeQuery(); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
            List<Tour> listTour = new ArrayList<>();
        while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
            String id = result.getString("id"); //Getters extract corresponding data from column names
            String name = result.getString("name");
            String type = result.getString("type");
            String customerType = result.getString("customerType");
            Date startDay = result.getDate("startDay");
            double price = result.getDouble("price");
            listTour.add(new Tour(id,name,type,customerType,startDay,price));
           
        }
            return listTour;
        
    }finally {
        if (statement != null) {
            try {
                statement.close();
                myConn.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }  
    }
//    public static void main(String[] args) throws SQLException {
//         String url = "jdbc:mysql://localhost:3306/quanlydiem"; //URL of database to be connected
//        Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
//        Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB
//
//        String query0="CREATE TABLE IF NOT EXISTS USER ("+  // Initial query to create table if not already present in DB
//                "name VARCHAR(30) NOT NULL," +
//                "role VARCHAR(30) NOT NULL," +
//                "email VARCHAR(30) NOT NULL PRIMARY KEY" +
//                ")";
//
//        statement.executeUpdate(query0); //executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) commands
//
//
//    String query1 = "INSERT INTO USER VALUES (?, ?, ? ,? ,?)";
//            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
//            preStat.setString(1, "admin");
//            preStat.setString(2, "Quang"); //Using the setter methods to substitute values corresponding to the ?s
//            preStat.setString(3, "admin");
//            preStat.setString(4, "admin@gmail.com");
//            preStat.setString(5, "admin");
//            preStat.executeUpdate(); //Executing the statement using executeUpdate()
//
//
//        String query2 = "SELECT * FROM USER;";
//
//        ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
//
//        while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
//            String role = result.getString("role"); //Getters extract corresponding data from column names
//            String name = result.getString("name");
//            String email = result.getString("email");
//            System.out.println("Name - " + name);
//            System.out.println("Role - " + role);
//            System.out.println("Email - " + email);
//        }
//       
//    }
}