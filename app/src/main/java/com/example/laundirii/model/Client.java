package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;

import java.io.Serializable;

public class Client implements Serializable {
    private int customerID;
    private String username;
    private String password;
    private String name;
    private String contactNo;
    private String address;
    private int paymentInfo;
    private Connect dbHelper;


    //constructors
    public Client()
    {
        username = "";
        password = "";
        name  = "";
        contactNo = "";
        address = "";
        paymentInfo = 0;
    }

    public Client(int customerID,String user,String pass,String name,String contact,String address, int payment)
    {
        this.customerID = customerID;
        this.username = user;
        this.password = pass;
        this.name = name;
        this.contactNo = contact;
        this.address = address;
        this.paymentInfo = payment;
    }

    public Client(String user,String pass,String name,String contact,String address, int payment)
    {
        this.username = user;
        this.password = pass;
        this.name = name;
        this.contactNo = contact;
        this.address = address;
        this.paymentInfo = payment;
    }

    public boolean loginClient(String username, String password, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.loginClient(username,password);
    }

    public boolean checkClientByUsername(String username, Context context) {
        // Implement logic to check if a client with the given username exists in the database
        // Example: Assuming you have a Connect class for database operations
        dbHelper = new Connect(context);
        return dbHelper.checkClientByUsername(username);
    }

    public Client getClient(String username, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getClient(username);
    }
    public Client getClient(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getClient(clientID);
    }

    // REGISTER
    public boolean insertClient(String username, String password, String name, String contactNo, String address, int paymentInfo, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertClient(username,password,name,contactNo,address,paymentInfo);
    }

    public int getCustomerID()
    {
        return this.customerID;
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

    public String getAddress()
    {
        return this.address;
    }

    public int getPaymentInfo()
    {
        return this.paymentInfo;
    }

    @Override
    public String toString()
    {
        return "Name: " + name + "\n"
                + "Contact No: " + contactNo + "\n"
                + "Address: " + address;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPaymentInfo(int paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void setDbHelper(Connect dbHelper) {
        this.dbHelper = dbHelper;
    }
}
