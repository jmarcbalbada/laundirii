package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.R;
import com.example.laundirii.databinding.WasherDashboardFragmentHistoryBinding;

public class WasherDashboardHistoryFragment extends Fragment {

    private WasherDashboardFragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WasherDashboardHistoryViewModel washerDashboardHistoryViewModel =
                new ViewModelProvider(this).get(WasherDashboardHistoryViewModel.class);

        binding = WasherDashboardFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.washerTextHistory;
        washerDashboardHistoryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}