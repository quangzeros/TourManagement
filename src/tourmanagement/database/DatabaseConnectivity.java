/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.database;

import java.sql.*;

/**
 * @author Aritra Chatterjee
 * JDBC sample program
 */

public class DatabaseConnectivity {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/quanlydiem"; //URL of database to be connected
        Connection myConn = DriverManager.getConnection(url, "root", "1234"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB

        String query0="CREATE TABLE IF NOT EXISTS USER ("+  // Initial query to create table if not already present in DB
                "name VARCHAR(30) NOT NULL," +
                "role VARCHAR(30) NOT NULL," +
                "email VARCHAR(30) NOT NULL PRIMARY KEY" +
                ")";

        statement.executeUpdate(query0); //executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) commands


            String query1 = "INSERT INTO USER VALUES (?, ?, ?)";
            PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
            preStat.setString(1, "Quang"); //Using the setter methods to substitute values corresponding to the ?s
            preStat.setString(2, "admin");
            preStat.setString(3, "admin@gmail.com");
            preStat.executeUpdate(); //Executing the statement using executeUpdate()


        String query2 = "SELECT * FROM USER;";

        ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object

        while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
            String role = result.getString("role"); //Getters extract corresponding data from column names
            String name = result.getString("name");
            String email = result.getString("email");
            System.out.println("Name - " + name);
            System.out.println("Role - " + role);
            System.out.println("Email - " + email);
        }
       
    }
}