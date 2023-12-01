package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_logs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.databinding.WasherDashboardFragmentLogsBinding;

public class WasherDashboardLogsFragment extends Fragment {

    private WasherDashboardFragmentLogsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WasherDashboardLogsViewModel washerDashboardLogsViewModel =
                new ViewModelProvider(this).get(WasherDashboardLogsViewModel.class);

        binding = WasherDashboardFragmentLogsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        washerDashboardLogsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}