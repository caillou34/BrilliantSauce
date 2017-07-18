package com.ge.predix.solsvc.boot.model;

/**
 * Created by ericblackmon on 7/14/17.
 */
public class voltObj {

    private static double voltage;

    public static double getVoltage(){

        return voltage;
    }

    public static void setVoltage(double volt){

        voltage= volt;
    }

}
