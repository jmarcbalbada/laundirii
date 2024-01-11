package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;

import java.io.Serializable;
import java.util.List;

public class Feedback implements Serializable {

    private int feedbackID;
    private String comment;
    private int rating;
    private Client client;
    private Courier courier;
    private Washer washer;
    private int phase1OrderID;
    private int phase2OrderID;
    private int hasRated;
    private Connect dbHelper;

    public Feedback(int feedbackID, String comment, int rating, Client client, Courier courier, Washer washer, int phase1OrderID, int phase2OrderID, int hasRated) {
        this.feedbackID = feedbackID;
        this.comment = comment;
        this.rating = rating;
        this.client = client;
        this.courier = courier;
        this.washer = washer;
        this.phase1OrderID = phase1OrderID;
        this.phase2OrderID = phase2OrderID;
        this.hasRated = hasRated;
    }

    public Feedback()
    {
        this.feedbackID = 0;
        this.comment = "";
        this.rating = 1;
        this.client = new Client();
        this.courier = new Courier();
        this.washer = new Washer();
        this.phase1OrderID = 0;
        this.phase2OrderID = 0;
        this.hasRated = 0;
    }


    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", client=" + client +
                ", courier=" + courier +
                ", washer=" + washer +
                ", phase1OrderID=" + phase1OrderID +
                ", phase2OrderID=" + phase2OrderID +
                '}';
    }

    public List<Phase1Order> getToFeedbackListOnPhase1(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getToFeedbackListOnPhase1(clientID);
    }

    public List<Phase2Order> getToFeedbackListOnPhase2(int clientID, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.getToFeedbackListOnPhase2(clientID);
    }

    public boolean insertFeedback(int clientID, String comment, int rating, int courierID, int washerID, int orderID, int typeOfOrder, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertFeedback(clientID, comment, rating, courierID, washerID, orderID, typeOfOrder);
    }

    public void updateWasherOverallRating(int washerID, Context context)
    {
        dbHelper = new Connect(context);
        dbHelper.updateWasherOverallRating(washerID);
    }

    public void updateCourierOverallRating(int courierID, Context context)
    {
        dbHelper = new Connect(context);
        dbHelper.updateCourierOverallRating(courierID);
    }


    public int getHasRated() {
        return hasRated;
    }

    public void setHasRated(int hasRated) {
        this.hasRated = hasRated;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public int getPhase1OrderID() {
        return phase1OrderID;
    }

    public void setPhase1OrderID(int phase1OrderID) {
        this.phase1OrderID = phase1OrderID;
    }

    public int getPhase2OrderID() {
        return phase2OrderID;
    }

    public void setPhase2OrderID(int phase2OrderID) {
        this.phase2OrderID = phase2OrderID;
    }

}
