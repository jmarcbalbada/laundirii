package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardPendingrequestViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private DashboardController dashboardController = new DashboardController();
    public WasherDashboardPendingrequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public List<Phase1Order> getListofOrdersToReceive(int washerID, Context context){
//        dashboardController.insertDummyPhase1Order(context);
        return dashboardController.WasherListofOrdersToReceive(washerID, context);
    }
}