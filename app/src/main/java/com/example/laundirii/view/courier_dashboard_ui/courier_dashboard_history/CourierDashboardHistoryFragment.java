package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.databinding.CourierDashboardFragmentHistoryBinding;

public class CourierDashboardHistoryFragment extends Fragment {

    private CourierDashboardFragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CourierDashboardHistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(CourierDashboardHistoryViewModel.class);

        binding = CourierDashboardFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}