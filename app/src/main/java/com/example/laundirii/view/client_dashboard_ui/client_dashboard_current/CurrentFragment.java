package com.example.laundirii.view.client_dashboard_ui.client_dashboard_current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.databinding.ClientFragmentCurrentBinding;

public class CurrentFragment extends Fragment {

    private ClientFragmentCurrentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CurrentViewModel currentViewModel =
                new ViewModelProvider(this).get(CurrentViewModel.class);

        binding = ClientFragmentCurrentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}