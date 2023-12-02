package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_logs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laundirii.model.Client;
import com.example.laundirii.model.Order;

import java.util.ArrayList;
import java.util.List;

public class WasherDashboardLogsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private Order order;

    public WasherDashboardLogsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");

    }

    public List<Order> PendingOrder(){
        List<Order> orders = new ArrayList<Order>();
        //TODO use a controller to get data from database
        return orders;
    }
    public LiveData<String> getText() {
        return mText;
    }
}