package com.example.laundirii.model;

import com.example.laundirii.database.Connect;

public class Phase2CourierPickup_Order {
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

    public Phase2CourierPickup_Order(int orderID, Client client, Washer washer, Courier courier,
                                     int courierStatus, double totalCourierAmount, String dateCourier,
                                     double totalDue, double totalPaid, int paymentStatus, String dateReceived) {
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
    }

    public Phase2CourierPickup_Order()
    {

    }

    @Override
    public String toString() {
        String paymentStat = paymentStatus == 0 ? "Unpaid" : "Paid";
        return "PICKUP from Washer to Client:" + "\n" +
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
}
