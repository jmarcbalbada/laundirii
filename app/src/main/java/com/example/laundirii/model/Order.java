package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;

public class Order {
    private int orderID;
    private Client client;
    private Washer washer;
    private Courier courier1;
    private double totalCourier1;
    private String dateCourier1;
    private Courier courier2;
    private double totalCourier2;
    private String dateCourier2;
    private double totalDue;
    private double totalPaid;
    private boolean paymentStatus;
    private double grandTotal;
    private String dateReceived;
    private Connect dbHelper;

    public Order()
    {
        client = new Client();
        washer = new Washer();
        courier1 = new Courier();
        totalCourier1 = 0.0;
        dateCourier1 = "";
        courier2 = new Courier();
        totalCourier2 = 0.0;
        dateCourier2 = "";
        totalDue = 0.0;
        totalPaid = 0.0;
        paymentStatus = false;
        grandTotal = 0.0;
        dateReceived = "";
    }

    public Order(Client client, Washer washer, Courier courier1, double totalCourier1, String dateCourier1,
                 Courier courier2, double totalCourier2, String dateCourier2, double totalDue,
                 double totalPaid, boolean paymentStatus, double grandTotal, String dateReceived)
    {
        this.client = client;
        this.washer = washer;
        this.courier1 = courier1;
        this.totalCourier1 = totalCourier1;
        this.dateCourier1 = dateCourier1;
        this.courier2 = courier2;
        this.totalCourier2 = totalCourier2;
        this.dateCourier2 = dateCourier2;
        this.totalDue = totalDue;
        this.totalPaid = totalPaid;
        this.paymentStatus = paymentStatus;
        this.grandTotal = grandTotal;
        this.dateReceived = dateReceived;
    }

    public boolean insertOrder(String orderID, Client client, Washer washer, Courier courier1,
                               double totalCourier1, String dateCourier1, Courier courier2,
                               double totalCourier2, String dateCourier2, double totalDue,
                               double totalPaid, boolean paymentStatus, double grandTotal,
                               String dateReceived, Context context)
    {
        dbHelper = new Connect(context);
        return true;
    }

}
