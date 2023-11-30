package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourierDashboardHistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CourierDashboardHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}