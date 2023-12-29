package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentNotificationBinding;
import com.example.laundirii.databinding.CourierDashboardFragmentNotificationBinding;

public class CourierNotificationFragment extends Fragment {
    private CourierDashboardFragmentNotificationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CourierDashboardFragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
