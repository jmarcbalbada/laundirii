package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;
import com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity;

import java.util.List;

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
        totalCourier1 = 50.0;
        dateCourier1 = "";
        courier2 = new Courier();
        totalCourier2 = 50.0;
        dateCourier2 = "";
        totalDue = 0.0;
        totalPaid = 0.0;
        paymentStatus = false;
        grandTotal = 0.0;
        dateReceived = "";
    }

    public Order(int orderID, Client client, Washer washer, Courier courier1, String dateCourier1,
                 Courier courier2, String dateCourier2, double totalDue,
                 double totalPaid, boolean paymentStatus, double grandTotal, String dateReceived)
    {
        this.orderID = orderID;
        this.client = client;
        this.washer = washer;
        this.courier1 = courier1;
        this.totalCourier1 = 50;
        this.dateCourier1 = dateCourier1;
        this.courier2 = courier2;
        this.totalCourier2 = 50;
        this.dateCourier2 = dateCourier2;
        this.totalDue = totalDue;
        this.totalPaid = totalPaid;
        this.paymentStatus = paymentStatus;
        this.grandTotal = grandTotal;
        this.dateReceived = dateReceived;
    }

    public Order(Client client, Washer washer, Courier courier1, String dateCourier1,
                 Courier courier2, String dateCourier2, double totalDue,
                 double totalPaid, boolean paymentStatus, double grandTotal, String dateReceived)
    {
        this.client = client;
        this.washer = washer;
        this.courier1 = courier1;
        this.totalCourier1 = 50;
        this.dateCourier1 = dateCourier1;
        this.courier2 = courier2;
        this.totalCourier2 = 50;
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

    public int getOrderID()
    {
        return this.orderID;
    }

    public Client getClient()
    {
        return this.client;
    }

    public Washer getWasher()
    {
        return this.washer;
    }

    public Courier getCourier1()
    {
        return this.courier1;
    }

    public double getTotalCourier1()
    {
        return this.totalCourier1;
    }

    public String getDateCourier1()
    {
        return this.dateCourier1;
    }

    public Courier getCourier2()
    {
        return this.courier2;
    }

    public double getTotalCourier2()
    {
        return this.totalCourier2;
    }

    public String getDateCourier2()
    {
        return this.dateCourier2;
    }

    public double getTotalDue()
    {
        return this.totalDue;
    }

    public double getTotalPaid()
    {
        return this.totalPaid;
    }

    public boolean getPaymentStatus()
    {
        return this.paymentStatus;
    }

    public double getGrandTotal()
    {
        return this.grandTotal;
    }

    public String getDateReceived()
    {
        return this.dateReceived;
    }

    public List<Order> getPendingDeliveriesOnCourier(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getPendingDeliveriesOnCourier(courierID);
    }

    public boolean insertDummyValuesOnOrder(Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertDummyOrder();
    }


    public String CourierOrdertoString(Order order)
    {
        return "Order ID: " + order.orderID + "\n" +
                "Customer: " + order.client.getName() + "\n" +
                "Customer Address: " + order.client.getAddress() + "\n" +
                "Washer: " + order.washer.getShopName() + "\n" +
                "Washer Address: " + order.washer.getShopLocation() + "\n" +
                "Due: " + order.totalDue + "\n";
    }

    @Override
    public String toString()
    {
        return "Order ID: " + this.orderID + "\n" +
                "Customer: " + this.client.getName() + "\n" +
                "Customer Address: " + this.client.getAddress() + "\n" +
                "Washer: " + this.washer.getShopName() + "\n" +
                "Washer Address: " + this.washer.getShopLocation() + "\n" +
                "Due: " + this.totalDue + "\n";
    }


}
