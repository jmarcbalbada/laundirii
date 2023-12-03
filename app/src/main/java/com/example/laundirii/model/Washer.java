package com.example.laundirii.model;
import android.content.Context;

import com.example.laundirii.database.Connect;
public class Washer {
    private int washerID;
    private String username;
    private String password;
    private String shopName;
    private String shopLocation;
    private String contactNo;
    private double ratePerKg;
    private boolean status;
    Connect dbHelper;

    //constructors
    public Washer()
    {
        username = "";
        password = "";
        shopName  = "";
        shopLocation  = "";
        contactNo = "";
        status = false;
        ratePerKg = 0.0;
    }

    public Washer(int washerID,String user,String pass,String name,String loc,String contact, double ratePerKg, boolean stat)
    {
        this.washerID = washerID;
        this.username = user;
        this.password = pass;
        this.shopName = name;
        this.shopLocation = loc;
        this.contactNo = contact;
        this.status = stat;
        this.ratePerKg = ratePerKg;
    }

    public Washer(String user,String pass,String name,String loc,String contact, double ratePerKg, boolean stat)
    {
        this.username = user;
        this.password = pass;
        this.shopName = name;
        this.shopLocation = loc;
        this.contactNo = contact;
        this.status = stat;
        this.ratePerKg = ratePerKg;
    }

    // GETTERS AND SETTERS

    public int getWasherID()
    {
        return this.washerID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getShopName()
    {
        return this.shopName;
    }

    public String getShopLocation()
    {
        return this.shopLocation;
    }

    public String getContactNo()
    {
        return this.contactNo;
    }

    public double getRatePerKg()
    {
        return this.ratePerKg;
    }

    public boolean getStatus()
    {
        return this.status;
    }

    public boolean loginWasher(String username, String password, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.loginWasher(username,password);
    }

    public boolean checkWasherByUsername(String username, Context context) {
        // Implement logic to check if a courier with the given username exists in the database
        // Example: Assuming you have a Connect class for database operations
        dbHelper = new Connect(context);
        return dbHelper.checkWasherByUsername(username);
    }

    // REGISTER

    public boolean insertWasher(String username, String password, String shopName, String shopLocation, String contactNo, int washerStatus, double ratePerKg, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertWasher(username,password,shopName,shopLocation,contactNo,washerStatus,ratePerKg);
    }

    @Override
    public String toString()
    {
        return "\nShop Name: " + shopName + "\n"
                + "Contact No: " + contactNo + "\n"
                + "Shop Location: " + shopLocation + "\n"
                + "Rate per kg.: " + ratePerKg + "\n";
    }

}
