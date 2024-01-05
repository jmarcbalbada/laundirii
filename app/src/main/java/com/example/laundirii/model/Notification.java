package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;


import java.io.Serializable;
import java.util.List;

public class Notification implements Serializable {

    private int NotificationID;
    private String title;
    private String message;
    // 0 - unread , 1 - read
    private boolean isRead;
    private Client client;
    private Courier courier;
    private Washer washer;
    private String dateTime;
    private Connect dbHelper;

    private Connect dbHelper;

    public Notification(int notificationID, String title, String message, boolean isRead, Client client, Courier courier, Washer washer, String dateTime) {
        NotificationID = notificationID;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.client = client;
        this.courier = courier;
        this.washer = washer;
        this.dateTime = dateTime;
    }


    public Notification() {
        NotificationID = 0;
        this.title = "";
        this.message = "";
        this.isRead = false;
        this.client = new Client();
        this.courier = new Courier();
        this.washer = new Washer();
        this.dateTime = "";
    }

    @Override
    public String toString() {
        return
                "\n" + this.title + "\n\n"
                + this.message + "\n"
                + this.dateTime + "\n";
    }

    public List<Notification> getNotificationOnClient(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getNotificationOnClient(clientID);
    }

    public List<Notification> getNotificationOnCourier(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getNotificationOnCourier(courierID);
    }

    public int getUnreadNotificationCount(int ID, int typeOfUser, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getUnreadNotificationCount(ID,typeOfUser);
    }

    public void markNotificationsAsRead(int ID, int typeOfUser, Context context)
    {
        dbHelper = new Connect(context);
        dbHelper.markNotificationsAsRead(ID,typeOfUser);
    }



    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Washer getWasher() {
        return washer;
    }

    public void setWasher(Washer washer) {
        this.washer = washer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Notification> getWasherNotification(int washerID, Context context) {
        dbHelper = new Connect(context);
        return dbHelper.getWasherNotification(washerID);
    }
    public void sendNotifications(int washerID, int customerID, int courierID, String notificaitonTitle, String notificationMessage, Context baseContext) {
        dbHelper = new Connect(baseContext);
        dbHelper.sendNotifications(washerID,customerID, courierID,notificaitonTitle ,notificationMessage);
    }
}
