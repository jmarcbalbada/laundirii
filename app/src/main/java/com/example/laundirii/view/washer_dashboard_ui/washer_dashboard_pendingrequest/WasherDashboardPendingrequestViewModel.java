package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WasherDashboardPendingrequestViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WasherDashboardPendingrequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}