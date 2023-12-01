package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_logs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WasherDashboardLogsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WasherDashboardLogsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}