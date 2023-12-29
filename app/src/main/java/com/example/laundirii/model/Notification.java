package com.example.laundirii.model;

public class Notification {
    private int NotificationID;
    private String title;
    private String message;
    private boolean isRead;
    private Client client;
    private Courier courier;
    private Washer washer;
    private String dateTime;

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

    @Override
    public String toString() {
        return "Notification{" +
                "NotificationID=" + NotificationID +
                ", Title='" + title + '\'' +
                ", Message='" + message + '\'' +
                ", Date='" + dateTime + '\'' +
                '}';
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
}
