package com.ge.predix.solsvc.boot.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by ericblackmon on 6/29/17.
 * used for methods accessing the postgress database
 * */
public class DBManager {

    private Connection conn;
    private static String GET_URL= "https://turbine-farm.run.aws-usw02-pr.ice.predix.io/api/turbines/";

    public DBManager(){
        conn= DBConn.getInstance();
    }

    /** for debug purposes only**/

    public Connection getConn(){
        return conn;
    }

    public String closeConn()

    {
        try {
            conn.close();
            return "Sucess";
        } catch (SQLException e) {
            return e.toString();
        }
    }

    public String getError(){

        return DBConn.connError;
    }


    public String readUsers(String name){

        try {
            conn.setAutoCommit(false);
            String first = "";
            String last = "";
            String email = "";
            String juice = "";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE firstname =" + name + ";");
            while (rs.next()) {
                int id = rs.getInt("id");
                first = rs.getString("firstname");
                last = rs.getString("lastname");
                email = rs.getString("email");
                juice = rs.getString("juicelevel");
            }
            rs.close();
            stmt.close();
            return first + " " + last + " " + " " + email + " " + juice;
        }catch(SQLException e){
            return e.toString();
        }

    }

    public String insertTurbVolt( int id, Object time, double voltage, String status){
        //call the getTurbine Method
        return null;
    }

    public String insertTurbTemp( int id, Object time, double temp, String status){
        //call the getTurbine Method
        return null;
    }

}
