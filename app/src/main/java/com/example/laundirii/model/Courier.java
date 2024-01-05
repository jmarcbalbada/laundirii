package com.example.laundirii.model;
import android.content.Context;

import com.example.laundirii.database.Connect;

import java.io.Serializable;

public class Courier implements Serializable {

    private int courierID;
    private String username;
    private String password;
    private String name;
    private String contactNo;
    private String plateNo;
    //courier status to identify if courier wanted to accept bookint
    private boolean status;
    Connect dbHelper;


    //constructors
    public Courier()
    {
        username = "";
        password = "";
        name  = "";
        contactNo = "";
        plateNo = "";
        status = false;
    }

    public Courier(String user,String pass,String name,String contact,String plate, boolean stat)
    {
        this.username = user;
        this.password = pass;
        this.name = name;
        this.contactNo = contact;
        this.plateNo = plate;
        this.status = stat;
    }

    public Courier(int courierID, String user,String pass,String name,String contact,String plate, boolean stat)
    {
        this.courierID = courierID;
        this.username = user;
        this.password = pass;
        this.name = name;
        this.contactNo = contact;
        this.plateNo = plate;
        this.status = stat;
    }

    public boolean loginCourier(String username, String password, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.loginCourier(username,password);
    }

    public boolean checkCourierByUsername(String username, Context context) {
        // Implement logic to check if a courier with the given username exists in the database
        // Example: Assuming you have a Connect class for database operations
        dbHelper = new Connect(context);
        return dbHelper.checkCourierByUsername(username);
    }

    // REGISTER
    public boolean insertCourier(String username, String password, String name, String contactNo, String plateNo, int courierStatus, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertCourier(username,password,name,contactNo,plateNo,courierStatus);
    }
    public Courier getCourier(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        Courier courier = dbHelper.getCourier(courierID);
        return courier;
    }
    public Courier getCourier(String username, Context context)
    {
        dbHelper = new Connect(context);
        Courier courier = dbHelper.getCourier(username);
        return courier;
    }

    public boolean setCourierStatusOnDatabase(int courierID, boolean status, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.setCourierStatusOnDatabase(courierID,status);
    }

    public boolean updateCourierStatusOnConnect(int courierID, int status, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.updateCourierStatus(courierID, status);
    }

    public boolean hasActiveTransactionOnPhase1Order(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.hasActiveTransactionOnPhase1Order(courierID);
    }

    public boolean hasCourierAlreadyReceivedPaymentPhase1(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.hasCourierAlreadyReceivedPaymentPhase1(courierID);
    }


//
//    public int getCourierStatusOnDb(int courierID, Context context)
//    {
//        dbHelper = new Connect(context);
//        return dbHelper.getCourierStatus(courierID);
//    }

    // GETTERS AND SETTERS

    public int getCourierID()
    {
        return this.courierID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getName()
    {
        return this.name;
    }

    public String getContactNo()
    {
        return this.contactNo;
    }

    public String getPlateNo()
    {
        return this.plateNo;
    }

    public boolean getStatus()
    {
        return this.status;
    }
    // set phase1order courier id to assign a random courier
//    public void setCourierID(int phase1OrderID,int courierID,Context context) {
//        dbHelper = new Connect(context);
//        dbHelper.setPhase1Courier(phase1OrderID,courierID);
//    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Courier ID: " + courierID + "\n"
                + "Name: " + name + "\n"
                + "Contact No: " + contactNo + "\n"
                + "Plate No: " + contactNo + "\n";
    }

    public int availableCourier(Context context){
        dbHelper = new Connect(context);
        return dbHelper.availableCourier();
    }
//    public void setCourierID(int courierID) {
//        this.courierID = courierID;
//    }

//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setContactNo(String contactNo) {
//        this.contactNo = contactNo;
//    }
//
//    public void setPlateNo(String plateNo) {
//        this.plateNo = plateNo;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
}
