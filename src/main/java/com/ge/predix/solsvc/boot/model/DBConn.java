package com.ge.predix.solsvc.boot.model;
import java.sql.*;
/**
 * Created by ericblackmon on 6/29/17.
 */
public class DBConn {

    /**at some point store these in a sperate file and then pull them
     */
    private static Connection instance= null;
    private static Connection conn;
    private static String hostname= "localhost";
    private static String dbname= "backend";
    private static String user="postgres";
    private static String password="f281330800B";
    private static String port= "5433";
    private static  String url = "jdbc:postgresql://"+hostname+":"+port+"/"+dbname;
    /** in case of error**/
    public static String connError="";

    private static Connection initCon() throws SQLException{
       conn= DriverManager.getConnection(url,user,password);
       return conn;
    }

    protected DBConn(){}

    public static Connection getInstance(){
        if(instance == null){
            synchronized (DBConn.class) {
                if(instance == null){
                    try {
                        instance=initCon();
                    }catch(SQLException e){
                        e.printStackTrace();
                        connError=printError(e);

                    }
                }
            }
        }
        return instance;
    }

    public static String printError(SQLException e){
        return e.toString();
    }
}
