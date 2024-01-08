package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;

import java.io.Serializable;
import java.util.List;

public class Phase2Order implements Serializable {
    private int orderID;
    private Client client;
    private Washer washer;
    private Courier courier;
    private int courierStatus;
    private double totalCourierAmount;
    private String dateCourier;
    private double totalDue;
    private double totalPaid;
    private int paymentStatus;
    private String dateReceived;

    private Connect dbHelper;
    public int getPhase2OrderStatus() {
        return phase2OrderStatus;
    }

    public void setPhase2OrderStatus(int phase2OrderStatus) {
        this.phase2OrderStatus = phase2OrderStatus;
    }

    /*
         -1 - Invalid / Cancelled

         COURIER COLLECT:
         10 - Client payment pending (Payment Status should be 1)
         11 - Courier on the way to Washer
         12 - Courier Arrived (Courier)
         13 - Washer Hands over the laundry to Courier and pays courier (Processing)
         14 - Received Client Clean Laundry (Courier must received the payment) (Processing)
         15 - Courier on the way to Client!
         16 - Courier arrived to Client
         17 - Completed!

         SELF COLLECT:
         20
         */
    private int phase2OrderStatus;

    public Phase2Order(int orderID, Client client, Washer washer, Courier courier,
                       int courierStatus, double totalCourierAmount, String dateCourier,
                       double totalDue, double totalPaid, int paymentStatus, String dateReceived, int phase2OrderStatus) {
        this.orderID = orderID;
        this.client = client;
        this.washer = washer;
        this.courier = courier;
        this.courierStatus = courierStatus;
        this.totalCourierAmount = totalCourierAmount;
        this.dateCourier = dateCourier;
        this.totalDue = totalDue;
        this.totalPaid = totalPaid;
        this.paymentStatus = paymentStatus;
        this.dateReceived = dateReceived;
        this.phase2OrderStatus = phase2OrderStatus;
    }

    public Phase2Order()
    {

    }

    @Override
    public String toString() {
        String paymentStat = paymentStatus == 0 ? "Unpaid" : "Paid";
        return "COLLECT from Washer to Client:" + "\n" +
                "Order ID =\t" + orderID + "\n" +
                "Client =\t" + client.getName() + "\n" +
                "Client Address =\t" + client.getAddress() + "\n" +
                "Washer =\t" + washer.getShopName() + "\n" +
                "Washer Address =\t" + washer.getShopLocation() + "\n" +
                "Courier =\t" + courier.getName() + "\n" +
                "Total Due =\t" + this.totalDue + "\n" +
                "Total Paid =\t" + this.totalPaid + "\n" +
                "Payment Status =\t" + paymentStat + "\n";
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Washer getWasher() {
        return washer;
    }

    public void setWasher(Washer washer) {
        this.washer = washer;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public int getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(int courierStatus) {
        this.courierStatus = courierStatus;
    }

    public double getTotalCourierAmount() {
        return totalCourierAmount;
    }

    public void setTotalCourierAmount(double totalCourierAmount) {
        this.totalCourierAmount = totalCourierAmount;
    }

    public String getDateCourier() {
        return dateCourier;
    }

    public void setDateCourier(String dateCourier) {
        this.dateCourier = dateCourier;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public List<Phase2Order> getWasherPhase2PendingOrder(int washerID, Context context) {
        dbHelper = new Connect(context);
        return dbHelper.getWasherPhase2PendingOrder(washerID);
    }
}
