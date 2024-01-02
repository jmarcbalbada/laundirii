package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardReceivedClothesAdapter extends RecyclerView.Adapter<WasherDashboardReceivedClothesAdapter.OrdersViewHolder> {

    private List<Phase1Order> orders;
    private Context context;

    public WasherDashboardReceivedClothesAdapter(List<Phase1Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_receivedclothes, parent, false);
        return new OrdersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewCourierStatus;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);
            // Initialize other TextViews or UI components as needed
        }

    }


}

