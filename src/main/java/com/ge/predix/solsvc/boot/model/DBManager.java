package com.ge.predix.solsvc.boot.model;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;


/**
 * Created by ericblackmon on 6/29/17.
 * used for methods accessing the postgress database
 * */
public class DBManager {

    private Connection conn;
    private static String GET_URL= "https://turbine-farm.run.aws-usw02-pr.ice.predix.io/api/turbines/";
    public final int MANAGER=1;
    public final int ENGINEER=2;


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


    public String checkRole(String email, String password){

        try {
            int role=0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.users WHERE email='" + email + "' AND password='" + password+ "';");
            while (rs.next()) {
                role = rs.getInt("role");
            }
            rs.close();
            stmt.close();
            if(role==MANAGER){
                return "Manager";
            }else if(role== ENGINEER){
                return "Engineer";
            }else{
                return role+"";
            }
        }catch(SQLException e){
            return e.toString();
        }

    }

public ArrayList<TempObj> getTempObjs(int id){
    TempObj obj=null;
    ArrayList<TempObj> tempList= new ArrayList<TempObj>();
    try {
        int iden=0;
        double value=0;
        double timeStamp=0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_temp WHERE id="+ id+";");
        while (rs.next()) {
            value = rs.getDouble("temp");
            timeStamp = rs.getDouble("time");
            iden = id;
            obj= new TempObj(value,iden,timeStamp);
            tempList.add(obj);
        }
        rs.close();
        stmt.close();
        return tempList;
    }catch(SQLException e){
        return tempList;
    }

}

public TempObj getTempObj(Double timeStamp){
    TempObj obj=null;
    try {
        int iden=0;
        double value=0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_temp WHERE time="+ timeStamp+";");
        while (rs.next()) {
            value = rs.getDouble("temp");
            timeStamp = timeStamp;
            iden =rs.getInt("id");
        }
        rs.close();
        stmt.close();

        obj= new TempObj(value,iden,timeStamp);
        return obj;
    }catch(SQLException e){
        return obj;
    }

}

public ArrayList<TempObj> getTempRange(double begin, double end){
    TempObj obj=null;
    ArrayList<TempObj>tempList= new ArrayList<TempObj>();
    try {
        int iden=0;
        double value=0;
        double timeStamp=0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_temp WHERE temp BETWEEN "+ begin+" AND " +end+ ";");
        while (rs.next()) {
            value = rs.getDouble("temp");
            timeStamp = rs.getDouble("time");
            iden =rs.getInt("id");
            obj= new TempObj(value,iden,timeStamp);
            tempList.add(obj);
        }
        rs.close();
        stmt.close();

        return tempList;
    }catch(SQLException e){
        return tempList;
    }
}




    public ArrayList<VoltObj> getVoltObjs(int id){
        VoltObj obj=null;
        ArrayList<VoltObj> tempList= new ArrayList<VoltObj>();
        try {
            int iden=0;
            double value=0;
            double timeStamp=0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_voltage WHERE id="+ id+";");
            while (rs.next()) {
                value = rs.getDouble("volt");
                timeStamp = rs.getDouble("time");
                iden = id;
                obj= new VoltObj(value,iden,timeStamp);
                tempList.add(obj);
            }
            rs.close();
            stmt.close();
            return tempList;
        }catch(SQLException e){
            return tempList;
        }

    }

    public VoltObj getVoltObj(Double timeStamp){
        VoltObj obj=null;
        try {
            int iden=0;
            double value=0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_voltage WHERE time="+ timeStamp+";");
            while (rs.next()) {
                value = rs.getDouble("volt");
                timeStamp = timeStamp;
                iden =rs.getInt("id");
            }
            rs.close();
            stmt.close();
            obj= new VoltObj(value,iden,timeStamp);
            return obj;
        }catch(SQLException e){
            return obj;
        }

    }

    public ArrayList<VoltObj> getVoltRange(double begin, double end){
        VoltObj obj=null;
        ArrayList<VoltObj>voltList= new ArrayList<VoltObj>();
        try {
            int iden=0;
            double value=0;
            double timeStamp=0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.turbine_voltage WHERE volt BETWEEN "+ begin+" AND " +end+ ";");
            while (rs.next()) {
                value = rs.getDouble("volt");
                timeStamp = rs.getDouble("time");
                iden =rs.getInt("id");
                obj= new VoltObj(value,iden,timeStamp);
                voltList.add(obj);
            }
            rs.close();
            stmt.close();

            return voltList;
        }catch(SQLException e){
            return voltList;
        }
    }


    public String insertTurbineVoltLive() {
        int affectedRows=0;
        try {

            for(int i=1;i<4;i++) {
                JSONObject obj =APIController.getTurbineData(i, "voltage", "sensors");
                String sql = "insert into public.turbine_voltage VALUES("+i+",?,?);";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, Double.parseDouble(obj.getString("timestamp")));
                pstmt.setDouble(2, Double.parseDouble(obj.getString("value")));
                affectedRows = pstmt.executeUpdate();
            }

            return affectedRows+ "";
            /* String sql2 ="insert into turbine_voltage VALUES (2,'"+obj2.getString("timestamp")+"','"+obj2.getString("value")+"');";
            stmt.execute(sql2);
            String sql3 ="insert into turbine_voltage VALUES (3,'"+obj3.getString("timestamp")+"','"+obj3.getString("value")+"');";
            stmt.execute(sql3);*/

        } catch (Exception e) {
            return e.toString();
        }
    }

    public String insertTurbineTempLive() {
        int affectedRows=0;
        try {

            for(int i=1;i<4;i++) {
                JSONObject obj =APIController.getTurbineData(i, "temperature", "sensors");
                String sql = "insert into public.turbine_temp VALUES("+i+",?,?);";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, Double.parseDouble(obj.getString("timestamp")));
                pstmt.setDouble(2, Double.parseDouble(obj.getString("value")));
                affectedRows = pstmt.executeUpdate();
            }

            return affectedRows+ "";
            /* String sql2 ="insert into turbine_voltage VALUES (2,'"+obj2.getString("timestamp")+"','"+obj2.getString("value")+"');";
            stmt.execute(sql2);
            String sql3 ="insert into turbine_voltage VALUES (3,'"+obj3.getString("timestamp")+"','"+obj3.getString("value")+"');";
            stmt.execute(sql3);*/

        } catch (Exception e) {
            return e.toString();
        }


    }

}
