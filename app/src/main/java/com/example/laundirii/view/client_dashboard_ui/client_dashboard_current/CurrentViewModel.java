package com.example.laundirii.view.client_dashboard_ui.client_dashboard_current;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CurrentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is current fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}