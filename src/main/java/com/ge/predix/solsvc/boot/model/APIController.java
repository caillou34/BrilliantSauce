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
 * Created by ericblackmon on 7/13/17.
 * used for methods accessing the postgress database
 * */

public class APIController{
    private static String GET_URL= "https://turbine-farm.run.aws-usw02-pr.ice.predix.io/api/turbines/";



    public  static String getTurbineData( int id, String dataType, String sensorType){
        int responseCode;
        try {
            URL voltageURL = new URL(GET_URL + id + "/" + sensorType + "/" + dataType);
            HttpURLConnection con = (HttpURLConnection) voltageURL.openConnection();
            con.setRequestMethod("GET");
            responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                return response.toString();
            } else {
                return"you played yourself";
            }

        } catch (Exception e){
            return e.toString();
        }
    }




}