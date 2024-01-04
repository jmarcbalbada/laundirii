package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;

import java.io.Serializable;
import java.util.List;

public class Phase1Order implements Serializable {

    private int orderID;
    private Client client;
    private Washer washer;
    private Courier courier;

    public Connect getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(Connect dbHelper) {
        this.dbHelper = dbHelper;
    }

    private int courierStatus;
    private double totalCourierAmount;
    // date randomly assigned
    private String dateCourier;
    private double totalDue;
    private double totalPaid;
    private int paymentStatus;
    //actual date received from courier to washer
    private String dateReceived;
    private int initialLoad;
    private int phase1OrderStatus;
    /*
     -1 - Invalid / Cancelled
     0 - Pending (Washer needs to click the transaction)
     1 - Courier on the way to Client
     2 - Courier Arrived (Courier)
     3 - Received Client Laundry / Courier on the way to Washer (Courier)
     4 - Washer Received the Laundry (Washer)
     5 - Washer is weighing your clothes!
     6 -
     ? - Completed
     */
    private String datePlaced;
    private Connect dbHelper;

    public Phase1Order(int orderID, Client client, Washer washer, Courier courier,
                       int courierStatus, double totalCourierAmount, String dateCourier,
                       double totalDue, double totalPaid, int paymentStatus, String dateReceived, int initialLoad, int phase1OrderStatus, String datePlaced) {
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
        this.initialLoad = initialLoad;
        this.phase1OrderStatus = phase1OrderStatus;
        this.datePlaced = datePlaced;
    }

    public Phase1Order()
    {
        orderID = -1;
        client = new Client();
        washer = new Washer();
        courier = new Courier();
        courierStatus = 0;
        totalCourierAmount = 0;
        dateCourier = "";
        totalDue = 0;
        totalPaid = 0;
        paymentStatus = -1;
        dateReceived = "";
        initialLoad = 0;
        phase1OrderStatus = 0;
        datePlaced = "";
    }

    public Phase1Order getPendingDeliveryOnCourier(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getPendingDeliveryOnCourier(courierID);
    }

    public List<Phase1Order> getPendingDeliveryOnClient(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getPendingDeliveryOnClient(clientID);
    }

    public List<Phase1Order> getHistoryList(String username, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getHistoryList(username);
    }

    public List<Phase1Order> getPendingRequestOnCourier(Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getPendingRequestOnCourier();
    }

    public List<Washer> getAvailableWashers(Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getAllWashers();
    }

    public boolean insertPhase1Order(int clientID, int washerID, int initialLoad, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertPhase1Order(clientID, washerID, initialLoad);
    }

    public boolean acceptPendingRequestOnCourier(int courierID, int orderID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.acceptPendingRequestOnCourier(courierID, orderID);
    }

    public boolean updatePhase1OrderStatusOnDb(int courierID, int status, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.updatePhase1OrderStatusOnDb(courierID, status);
    }

//    public boolean insertDummyPhase1Order(Context context)
//    {
//        dbHelper = new Connect(context);
//        return dbHelper.insertDummyPhase1Order();
//    }


    @Override
    public String toString() {
        if(courier == null)
        {
            courier = new Courier();
        }
        String paymentStat = paymentStatus == 0 ? "Unpaid" : "Paid";
        String orderStatus = "";

        switch(this.phase1OrderStatus)
        {
            case -1: orderStatus = "Invalid / Cancelled";
                break;
            case 0: orderStatus = "Pending";
                break;
            case 1: orderStatus = "Courier is on the way to Client!";
                break;
            case 2: orderStatus = "Courier has arrived!";
                break;
            case 3: orderStatus = "Courier is on the way to Washer!";
                break;
            case 4: orderStatus = "Washer Received the Laundry!";
                break;
        }

        return "PICKUP from Client to Washer" + "\n" +
                "Order ID:\t" + orderID + "\n" +
                "Order Status: \t" + orderStatus + "\n" +
                "Client:\t" + client.getName() + "\n" +
                "Client Address:\t" + client.getAddress() + "\n" +
                "Washer:\t" + washer.getShopName() + "\n" +
                "Washer Address:\t" + washer.getShopLocation() + "\n" +
                "Courier:\t" + courier.getName() + "\n" +
                "Total Due:\t" + this.totalDue + "\n" +
                "Total Paid:\t" + this.totalPaid + "\n" +
                "Initial Load:\t" + this.initialLoad + " kg\n" +
                "Date Placed:\t" + this.datePlaced + "\n" +
                "Payment Status:\t" + paymentStat + "\n";
    }

    // getters and setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getClientName(){
        return client.getName();
    }
    public Client getClient(){
        return client;
    }
    // getting the client all details using washerID that is initiallly set
    public Client getClient(Context context) {
        setClient(this.client.getClient(this.client.getCustomerID(),context));
        return this.client;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public Washer getWasher() {
        return washer;
    }
    // getting the washer all details using washerID that is initiallly set
    public Washer getWasher(Context context) {
        setWasher(this.washer.getWasher(this.washer.getWasherID(),context));
        return this.washer;

    }

    public void setWasher(Washer washer) {
        this.washer = washer;
    }

    public Courier getCourier() {
        return courier;
    }

    // getting the courier all details using washerID that is initiallly set
    public Courier getCourier(Context context) {
        setCourier(this.courier.getCourier(this.courier.getCourierID(),context));
        return courier;
    }
    public int getInitialLoad() {
        return this.initialLoad;
    }

    public void setInitialLoad(int initialLoad) {
        this.initialLoad = initialLoad;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public int getPhase1OrderStatus() {
        return phase1OrderStatus;
    }
    public void setPhase1OrderStatus(int phase1OrderStatus , Context context) {
        dbHelper = new Connect(context);
        dbHelper.setPhase1OrderStatus(this.orderID,phase1OrderStatus);
        return;
    }

    public void setPhase1OrderStatus(int phase1OrderStatus) {
        this.phase1OrderStatus = phase1OrderStatus;
    }

    public boolean setPhase1OrderStatus2(int phase1OrderID, int phase1OrderStatus, Context context) {
        if(context == null)
        {
            return false;
        }
        dbHelper = new Connect(context);
        return dbHelper.setReturnPhase1OrderStatus(phase1OrderID,phase1OrderStatus);
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


    public void setCourierID(int courierID){
        courier.setCourierID(courierID);
    }
    public void setClientID(int clientID){
        client.setCustomerID(clientID);
    }
    public void setWasherID(int washerID)
    {
        washer.setWasherID(washerID);
    }

    public void getCourierID(int courierID){
        courier.setCourierID(courierID);
    }
    public int getClientID(){
        return client.getCustomerID();
    }
    public void getWasherID(int washerID)
    {
        washer.setWasherID(washerID);
    }
    public List<Phase1Order> getPendingDeliveriesOnWasher(int washerID, Context context){
        dbHelper = new Connect(context);
        return dbHelper.getPendingDeliveriesOnWasher(washerID, context);
    }

    public int washerAcceptClientRequest(int phase1OrderID, int availableCourierID, Context baseContext) {
        dbHelper = new Connect(baseContext);
        return dbHelper.washerAcceptClientRequest(phase1OrderID,availableCourierID);
    }

    public  List<Phase1Order> getWasherHistory(int washerID, Context context) {
        dbHelper = new Connect(context);
        return dbHelper.getWasherHistory(washerID,context);

    }

    public List<Phase1Order> getWasherReceivedClothes(int washerID, Context context) {
        dbHelper = new Connect(context);
        return dbHelper.getWasherReceivedClothes(washerID,context);
    }
    public int washerMarkedClothesAsReceived(int orderID, Context context){
        dbHelper = new Connect(context);
        return dbHelper.washerMarkedClothesAsReceived(orderID,context);

    }

    public void washerUpdatePhase1OrderCourierIDAndCourierDate(int orderID, int availableCourierID,Context applicationContext) {
        dbHelper = new Connect(applicationContext);
        dbHelper.washerUpdatePhase1OrderCourierIDAndCourierDate(orderID, availableCourierID);

    }

    public void updatePhase1OrderDateReceivedToCurrentDate(int orderID, Context baseContext) {
        dbHelper = new Connect(baseContext);
        dbHelper.updatePhase1OrderDateReceivedToCurrentDate(orderID);
    }

    public void updatePhase1OrderTotalDue(int orderID, double totalDue, Context baseContext) {
        dbHelper = new Connect(baseContext);
        dbHelper.updatePhase1OrderTotalDue(orderID,totalDue);
    }
}
