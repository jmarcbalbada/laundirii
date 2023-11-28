package com.example.laundirii.model;
import android.content.Context;

import com.example.laundirii.database.Connect;

public class Courier {

    private String courierID;
    private String username;
    private String password;
    private String name;
    private String contactNo;
    private String plateNo;
    private boolean status;
    Connect dbHelper;

    //constructors
    public Courier()
    {
        courierID = "";
        username = "";
        password = "";
        name  = "";
        contactNo = "";
        plateNo = "";
        status = false;
    }

    public Courier(String courID,String user,String pass,String name,String contact,String plate, boolean stat)
    {
        this.courierID = courID;
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
}
