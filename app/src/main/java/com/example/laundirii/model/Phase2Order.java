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

    /*
     -1 - Invalid / Cancelled
     0 - General Pending

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
    private String referenceNo;
    private String datePlaced;
    private int phase2_phase1OrderID;
    private Connect dbHelper;

    public Phase2Order(int orderID, Client client, Washer washer, Courier courier,
                       int courierStatus, double totalCourierAmount, String dateCourier,
                       double totalDue, double totalPaid, int paymentStatus, String dateReceived,
                       int phase2OrderStatus, String referenceNo, String datePlaced, int phase2_phase1OrderID) {
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
        this.referenceNo = referenceNo;
        this.datePlaced = datePlaced;
        this.phase2_phase1OrderID = phase2_phase1OrderID;
    }

    public Phase2Order()
    {
        client = new Client();
        washer = new Washer();
        courier = new Courier();
        courierStatus = 0;
        totalCourierAmount = 0;
        dateCourier = "";
        totalDue = 0;
        totalPaid = 0;
        paymentStatus = 0;
        phase2OrderStatus = 0;
        referenceNo = "";
        datePlaced = "";
        phase2_phase1OrderID = 0;
    }

    @Override
    public String toString() {
        if(courier == null)
        {
            courier = new Courier();
        }

        String paymentStat = paymentStatus == 0 ? "Unpaid" : "Paid";
        String orderStatus = "";
        switch(this.phase2OrderStatus)
        {
            case -1: orderStatus = "Invalid / Cancelled";
                break;
            case 0: orderStatus = "Pending";
                break;
            // COURIER COLLECT
            case 10: orderStatus = "Client payment pending!";
                break;
            case 11: orderStatus = "Courier on the way to Washer!";
                break;
            case 12: orderStatus = "Courier has arrived to Washer";
                break;
            case 13: orderStatus = "Processing1";
                break;
            case 14: orderStatus = "Processing2";
                break;
            case 15: orderStatus = "Courier on the way to Client!";
                break;
            case 16: orderStatus = "Courier has arrived to Client";
                break;
            case 17: orderStatus = "Completed!";
                break;

        }

        if(phase2OrderStatus >= 10 && phase2OrderStatus <= 19)
        {
            return "COURIER COLLECT from Washer to Client:" + "\n" +
                    "Order ID: \t" + orderID + "\n" +
                    "Order Status: \t" + orderStatus + "\n" +
                    "Client: \t" + client.getName() + "\n" +
                    "Client Address: \t" + client.getAddress() + "\n" +
                    "Washer: \t" + washer.getShopName() + "\n" +
                    "Washer Address: \t" + washer.getShopLocation() + "\n" +
                    "Courier: \t" + courier.getName() + "\n" +
                    "Total Due:\t" + this.totalDue + "\n" +
                    "Total Paid:\t" + this.totalPaid + "\n" +
                    "Date Placed:\t" + this.datePlaced + "\n" +
                    "Payment Status: \t" + paymentStat + "\n";
        }
        else if(phase2OrderStatus >= 20)
        {
            return "SELF COLLECT from Washer to Client:" + "\n" +
                    "Order ID: \t" + orderID + "\n" +
                    "Order Status: \t" + orderStatus + "\n" +
                    "Client: \t" + client.getName() + "\n" +
                    "Client Address: \t" + client.getAddress() + "\n" +
                    "Washer: \t" + washer.getShopName() + "\n" +
                    "Washer Address: \t" + washer.getShopLocation() + "\n" +
                    "Total Due:\t" + this.totalDue + "\n" +
                    "Total Paid:\t" + this.totalPaid + "\n" +
                    "Date Placed:\t" + this.datePlaced + "\n" +
                    "Payment Status: \t" + paymentStat + "\n";
        }

        return "COLLECT from Washer to Client:" + "\n" +
                "Order ID: \t" + orderID + "\n" +
                "Order Status: \t" + orderStatus + "\n" +
                "Client: \t" + client.getName() + "\n" +
                "Client Address: \t" + client.getAddress() + "\n" +
                "Washer: \t" + washer.getShopName() + "\n" +
                "Washer Address: \t" + washer.getShopLocation() + "\n" +
                "Courier: \t" + courier.getName() + "\n" +
                "Total Due:\t" + this.totalDue + "\n" +
                "Total Paid:\t" + this.totalPaid + "\n" +
                "Date Placed:\t" + this.datePlaced + "\n" +
                "Payment Status: \t" + paymentStat + "\n";
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public List<Phase2Order> getPendingCollectOnClient(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getPendingCollectOnClient(clientID);
    }

    public boolean updateReferenceNo(int clientID,String referenceNo, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.updateReferenceNo(clientID,referenceNo);
    }

    public int getPhase2_phase1OrderID() {
        return phase2_phase1OrderID;
    }

    public void setPhase2_phase1OrderID(int phase2_phase1OrderID) {
        this.phase2_phase1OrderID = phase2_phase1OrderID;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public Connect getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(Connect dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int getPhase2OrderStatus() {
        return phase2OrderStatus;
    }

    public void setPhase2OrderStatus(int phase2OrderStatus) {
        this.phase2OrderStatus = phase2OrderStatus;
    }

    public boolean insertPhase2Order(int clientID, int washerID, double totalDue, int phase2_phase1OrderID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertPhase2Order(clientID,washerID,totalDue, phase2_phase1OrderID);
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
