package com.example.laundirii.controller;

import com.example.laundirii.database.Connect;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Order;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private Client client;
    private Courier courier;
    private Washer washer;
    private Order order;
    private Phase1Order phase1Order;

    public DashboardController()
    {
        client = new Client();
        courier = new Courier();
        washer = new Washer();
        order = new Order();
        phase1Order = new Phase1Order();
    }
    public DashboardController(Client client)
    {
        this.client = client;
    }
    public DashboardController(Courier courier)
    {
        this.courier = courier;
    }
    public DashboardController(Washer washer)
    {
        this.washer = washer;
    }

    public Client getClient(String username, Context context)
    {
        Client fetchClient = client.getClient(username,context);
        return fetchClient;
    }
    public Courier getCourier(String username, Context context)
    {
        Courier fetchCourier = courier.getCourier(username,context);
        return fetchCourier;
    }

    public Phase1Order getPendingDeliveryOnCourier(int courierID, Context context)
    {
        return phase1Order.getPendingDeliveryOnCourier(courierID, context);
    }

    public boolean insertDummyPhase1Order(Context context)
    {
        return phase1Order.insertDummyPhase1Order(context);
    }

//    public List<Order> getPendingDeliveriesOnCourier(int courierID, Context context)
//    {
//        return order.getPendingDeliveriesOnCourier(courierID,context);
//    }

//    public boolean insertDummyValuesOnOrder(Context context)
//    {
//        return order.insertDummyValuesOnOrder(context);
//    }
}
