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
        WasherDashboardPendingrequestViewModel washerDashboardPendingrequestViewModel =
                new ViewModelProvider(this).get(WasherDashboardPendingrequestViewModel.class);

        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        washerDashboardPendingrequestViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}