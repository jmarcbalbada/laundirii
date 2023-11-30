package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourierSlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CourierSlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}