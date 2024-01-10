package com.example.laundirii.model;

import java.io.Serializable;

public class Feedback implements Serializable {

    private int feedbackID;
    private String comment;
    private int rating;
    public Feedback()
    {
        comment = "";
        rating = 1;
    }

    public Feedback(String comment, int rating)
    {
        this.comment = comment;
        this.rating = rating;
    }
}
