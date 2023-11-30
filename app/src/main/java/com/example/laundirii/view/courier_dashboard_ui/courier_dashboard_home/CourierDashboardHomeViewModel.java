package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourierDashboardHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CourierDashboardHomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}