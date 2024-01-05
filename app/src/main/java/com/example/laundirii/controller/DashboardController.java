package com.example.laundirii.controller;

import android.content.Context;

import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Notification;
import com.example.laundirii.model.Order;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private Client client;
    private Courier courier;
    private Washer washer;
    private Order order;
    private Phase1Order phase1Order;
    private Notification notification;

    public DashboardController()
    {
        client = new Client();
        courier = new Courier();
        washer = new Washer();
        order = new Order();
        phase1Order = new Phase1Order();
        notification = new Notification();
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
    public Washer getWasher(String username, Context context)
    {
        return washer.getWasher(username,context);
    }

    public Phase1Order getPendingDeliveryOnCourier(int courierID, Context context)
    {
        return phase1Order.getPendingDeliveryOnCourier(courierID, context);
    }

    public List<Notification> getNotificationOnClient(int clientID, Context context)
    {
        return notification.getNotificationOnClient(clientID, context);
    }

    public List<Notification> getNotificationOnCourier(int courierID, Context context)
    {
        return notification.getNotificationOnCourier(courierID, context);
    }

    public int getUnreadNotificationCount(int ID, int typeOfUser, Context context)
    {
        return notification.getUnreadNotificationCount(ID,typeOfUser,context);
    }

    public void markNotificationsAsRead(int ID, int typeOfUser, Context context)
    {
        notification.markNotificationsAsRead(ID,typeOfUser,context);
    }



    public List<Phase1Order> getPendingDeliveryOnClient(int clientID, Context context)
    {
        return phase1Order.getPendingDeliveryOnClient(clientID,context);
    }

    public List<Phase1Order> getHistoryList(String username, Context context)
    {
        return phase1Order.getHistoryList(username,context);
    }

    public List<Phase1Order> getPendingRequestOnCourier(Context context)
    {
        return phase1Order.getPendingRequestOnCourier(context);
    }


    public List<Washer> getAvailableWashers(Context context)
    {
        return phase1Order.getAvailableWashers(context);
    }

    public boolean updatePhase1OrderStatusOnDb(int courierID, int status, Context context)
    {
        return phase1Order.updatePhase1OrderStatusOnDb(courierID,status,context);
    }

    public boolean acceptPendingRequestOnCourier(int courierID, int orderID, Context context)
    {
        return phase1Order.acceptPendingRequestOnCourier(courierID,orderID,context);
    }

    public boolean setCourierStatusOnDatabase(int courierID, boolean status, Context context)
    {
        return courier.setCourierStatusOnDatabase(courierID,status,context);
    }


    public boolean hasActiveTransactionOnPhase1Order(int courierID, Context context)
    {
        return courier.hasActiveTransactionOnPhase1Order(courierID, context);
    }

    public boolean hasCourierAlreadyReceivedPaymentPhase1(int courierID, Context context)
    {
        return courier.hasCourierAlreadyReceivedPaymentPhase1(courierID, context);
    }

    public boolean setCourierStatusPhase1OrderOnDatabase(int courierID, boolean status, Context context)
    {
        return phase1Order.setCourierStatusPhase1OrderOnDatabase(courierID,status, context);
    }

    public boolean updateCourierStatus(int courierID, int status, Context context)
    {
        return courier.updateCourierStatusOnConnect(courierID,status, context);
    }

    public boolean updatePhase1OrderStatus(int phase1OrderID, int status, Context context)
    {
        if(context == null)
        {
            return false;
        }
        return phase1Order.setPhase1OrderStatus2(phase1OrderID, status, context);
    }

//    public int getCourierStatusOnDb(int courierID, Context context)
//    {
//        return courier.getCourierStatusOnDb(courierID,context);
//    }

    public boolean insertPhase1Order(int clientID, int washerID,int initialLoad, Context context)
    {
        return phase1Order.insertPhase1Order(clientID,washerID,initialLoad,context);
    }

//    public boolean insertDummyPhase1Order(Context context)
//    {
//        return phase1Order.insertDummyPhase1Order(context);
//    }

//    public List<Order> getPendingDeliveriesOnCourier(int courierID, Context context)
//    {
//        return order.getPendingDeliveriesOnCourier(courierID,context);
//    }

//    public boolean insertDummyValuesOnOrder(Context context)
//    {
//        return order.insertDummyValuesOnOrder(context);
//    }

    public List<Phase1Order> getPendingDeliveriesOnWasher(int washerID,Context context){
        return phase1Order.getPendingDeliveriesOnWasher(washerID, context);
    }
    public List<Phase1Order> getListofOrdersToReceive(int washerID, Context context){
//        dashboardController.insertDummyPhase1Order(context);
        List<Phase1Order> e = new ArrayList<Phase1Order>();
        return e;
    }
    public int availableCourier(Context context){
        return courier.availableCourier(context);
    }

    public int washerAcceptClientRequest(int phase1OrderID, int availableCourierID, Context baseContext) {
        return phase1Order.washerAcceptClientRequest(phase1OrderID,availableCourierID,baseContext);
    }

    public List<Phase1Order> getWasherHistory(int washerID, Context context) {
        return phase1Order.getWasherHistory(washerID,context);
    }

    public List<Phase1Order> getWasherReceivedClothes(int washerID, Context context) {
        return phase1Order.getWasherReceivedClothes(washerID,context);
    }

    public int washerMarkedClothesAsReceived(int orderID, Context context) {
        return phase1Order.washerMarkedClothesAsReceived(orderID,context);
    }

    public void washerUpdatePhase1OrderCourierIDAndCourierDate(int orderID, int availableCourierID, Context applicationContext) {
        phase1Order.washerUpdatePhase1OrderCourierIDAndCourierDate(orderID, availableCourierID, applicationContext);
    }

    public void updatePhase1OrderDateReceivedToCurrentDate(int orderID, Context baseContext) {
        phase1Order.updatePhase1OrderDateReceivedToCurrentDate(orderID, baseContext);
    }

    public void updatePhase1OrderTotalDue(int orderID, double totalDue, Context baseContext) {
        phase1Order.updatePhase1OrderTotalDue(orderID, totalDue,baseContext);
    }

    public void updatePhase1OrderInitialLoad(int orderID, int initialload, Context baseContext) {
        phase1Order.updatePhase1OrderInitialLoad(orderID, initialload,baseContext);
    }
}
