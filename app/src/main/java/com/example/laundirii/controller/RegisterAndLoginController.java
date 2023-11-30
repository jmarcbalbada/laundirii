package com.example.laundirii.controller;

import android.content.Context;

import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Washer;

public class RegisterAndLoginController {
    private Client client;
    private Courier courier;
    private Washer washer;

    // constructors
    public RegisterAndLoginController()
    {
        client = new Client();
        courier = new Courier();
        washer = new Washer();
    }
    public RegisterAndLoginController(Client client)
    {
        this.client = client;
    }
    public RegisterAndLoginController(Courier courier)
    {
        this.courier = courier;
    }
    public RegisterAndLoginController(Washer washer)
    {
        this.washer = washer;
    }

    //  DETERMINE TYPE OF USER FOR LOGIN
    public int typeOfUser(String username, Context context)
    {
        // 0 - Client, 1 - Courier, 2 - Washer
        int typeOfUserResult = 0;
        if(client.checkClientByUsername(username,context))
        {
            typeOfUserResult = 0;
        }else if(courier.checkCourierByUsername(username,context))
        {
            typeOfUserResult = 1;
        }else if(washer.checkWasherByUsername(username,context))
        {
            typeOfUserResult = 2;
        }
        else
        {
            // not found
            typeOfUserResult = -1;
        }
        return typeOfUserResult;
    }

    // LOG-IN LOGIC

    public boolean loginClient(String username, String password, Context context)
    {
        return client.loginClient(username,password,context);
    }

    public boolean loginCourier(String username, String password, Context context)
    {
        return courier.loginCourier(username,password,context);
    }

    public boolean loginWasher(String username, String password, Context context)
    {
        return washer.loginWasher(username,password,context);
    }

    // REGISTER LOGIC


    public boolean registerClient(String username, String password, String name, String contactNo, String address, int paymentInfo, Context context)
    {
        return client.insertClient(username,password,name,contactNo,address,paymentInfo,context);
    }

    public boolean registerCourier(String username, String password, String name, String contactNo, String plateNo, int courierStatus, Context context)
    {
        return courier.insertCourier(username,password,name,contactNo,plateNo,courierStatus,context);
    }

    public boolean registerWasher(String username, String password, String shopName, String shopLocation, String contactNo, int washerStatus, double ratePerKg, Context context)
    {
        return washer.insertWasher(username,password,shopName,shopLocation,contactNo,washerStatus,ratePerKg, context);
    }




}
