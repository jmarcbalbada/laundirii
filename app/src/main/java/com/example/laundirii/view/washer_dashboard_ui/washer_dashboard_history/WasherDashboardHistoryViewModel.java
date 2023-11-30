package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WasherDashboardHistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WasherDashboardHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}