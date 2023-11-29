package com.example.laundirii.model;

public class Feedback {

    private int feedbackID;
    private Order order;
    private String comment;
    private int rating;

    public Feedback()
    {
        order = new Order();
        comment = "";
        rating = 1;
    }

    public Feedback(Order order, String comment, int rating)
    {
        this.order = order;
        this.comment = comment;
        this.rating = rating;
    }
}
