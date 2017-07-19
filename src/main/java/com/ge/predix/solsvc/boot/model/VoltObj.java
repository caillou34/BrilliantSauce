package com.ge.predix.solsvc.boot.model;

/**
 * Created by ericblackmon on 7/18/17.
 */
public class VoltObj {
    private double value;

    private int id;

    private double timestamp;

    public VoltObj(double value, int id, double timestamp){
        this.value=value;
        this.id=id;
        this.timestamp=timestamp;
    }

    public int getID(){
        return id;
    }
    public double getValue(){
        return value;
    }
    public double getTimestamp(){
        return timestamp;
    }

    public void setID(int id){
        this.id=id;
    }
    public void setValue(int value){
        this.value=value;
    }
    public void setTimestamp(int timestamp){
        this.timestamp=timestamp;
    }

    public String toString(){
        return "ID: "+getID()+", Value: "+getValue()+", Timestamp:"+ getTimestamp();
    }
}
