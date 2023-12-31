package com.example.laundirii.view.client_dashboard_ui.client_dashboard_collect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentCollectBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.view.client_dashboard_ui.ClientCourierCollectActivity;
import com.example.laundirii.view.client_dashboard_ui.ClientSelfCollectActivity;

import java.util.List;

public class ClientCollectFragment extends Fragment {
    private DashboardController dashboardController;
    private ClientFragmentCollectBinding binding;
    private Client client;
    private SharedPreferences clientInfoPreferences;
    private ListView lv_CollectOrders;
    private ArrayAdapter CollectClientOrdersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ClientFragmentCollectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences", 0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this.getActivity());
        Log.e("thisClient", client.toString());
        lv_CollectOrders = root.findViewById(R.id.client_collect_transaction);
        displayCollectOrders();

        lv_CollectOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                Phase2Order selectedOrder = (Phase2Order) parent.getItemAtPosition(position);

                switch(selectedOrder.getPhase2OrderStatus())
                {
                    case -1:
                        break;
                    case 0: showCustomDialogSelectCollect(selectedOrder);
                        break;
                }
            }
        });
        return root;
    }

    private void displayCollectOrders() {
        List<Phase2Order> listPendingOrders = dashboardController.getPendingCollectOnClient(client.getCustomerID(), getActivity());
        Log.e("listCollectOrders", listPendingOrders.size() + "");
        CollectClientOrdersAdapter = new ArrayAdapter<Phase2Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
        lv_CollectOrders.setAdapter(CollectClientOrdersAdapter);
    }

    private void showCustomDialogSelectCollect(Phase2Order phase2Order) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_client_select_collect_method, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewNotYetButton = dialogView.findViewById(R.id.textViewClientSelectCollectContentNoButton);
        TextView textViewSelfCollectButton = dialogView.findViewById(R.id.textViewClientSelectCollectContentSelfCollect);
        TextView textViewCourierCollectButton = dialogView.findViewById(R.id.textViewClientSelectCollectContentCourierCollect);
        textViewNotYetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textViewSelfCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ClientSelfCollectActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        textViewCourierCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ClientCourierCollectActivity.class);
                intent.putExtra("PHASE2_ORDER_EXTRA",phase2Order);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }



}
