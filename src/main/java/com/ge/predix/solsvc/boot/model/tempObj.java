package com.ge.predix.solsvc.boot.model;

/**
 * Created by ericblackmon on 7/14/17.
 */
public class tempObj {

    private static double temp;

    public static double getTemp(){

        return temp;
    }

    public static void setTemp(double temperature){

        temp= temperature;
    }

    public String toString(){

        return temp+"";
    }

}
