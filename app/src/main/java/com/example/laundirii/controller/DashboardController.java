package com.example.laundirii.controller;

import com.example.laundirii.database.Connect;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Order;
import com.example.laundirii.model.Washer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private Client client;
    private Courier courier;
    private Washer washer;
    private Order order;

    public DashboardController()
    {
        client = new Client();
        courier = new Courier();
        washer = new Washer();
        order = new Order();
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

    public Courier getCourier(String username, Context context)
    {
        Courier fetchCourier = courier.getCourier(username,context);
        return fetchCourier;
    }

    public List<Order> getPendingDeliveriesOnCourier(int courierID, Context context)
    {
        return order.getPendingDeliveriesOnCourier(courierID,context);
    }

    public boolean insertDummyValuesOnOrder(Context context)
    {
        return order.insertDummyValuesOnOrder(context);
    }
}
