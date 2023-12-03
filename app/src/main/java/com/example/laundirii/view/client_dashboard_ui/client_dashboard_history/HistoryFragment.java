package com.example.laundirii.view.client_dashboard_ui.client_dashboard_history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentHistoryBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_current.CurrentFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ClientFragmentHistoryBinding binding;
    private Client client;
    SharedPreferences clientInfoPreferences;
    private ListView lv_historyList;
    private ArrayAdapter historyClientOrdersAdapter;
    private DashboardController dashboardController;
    private Button bookServiceButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ClientFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());
        lv_historyList = (ListView) root.findViewById(R.id.history_transaction);
        bookServiceButton = root.findViewById(R.id.btn_book_service);
        displayHistory();

        bookServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Book Service", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HistoryFragment.this.getContext(), BookServiceActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    public void displayHistory()
    {
        List<Phase1Order> listHistory = new ArrayList<>();
        listHistory = dashboardController.getHistoryList(client.getUsername(), this.getActivity());
        historyClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
        lv_historyList.setAdapter(historyClientOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
