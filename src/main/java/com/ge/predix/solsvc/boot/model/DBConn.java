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
    private static String hostname= "db-60e3a2c2-1a48-45b5-9cfa-2b6af0287777.c7uxaqxgfov3.us-west-2.rds.amazonaws.com";
    private static String dbname= "postgres";
    private static String user="u5n02zy3x4sd3hzd";
    private static String password="nmq58dcxp4f9tuvodl9ieqigb";
    private static String port= "5432";
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
