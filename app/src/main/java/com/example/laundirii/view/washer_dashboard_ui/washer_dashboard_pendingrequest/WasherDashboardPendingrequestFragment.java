package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;

public class WasherDashboardPendingrequestFragment extends Fragment {

    private WasherDashboardFragmentPendingrequestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}