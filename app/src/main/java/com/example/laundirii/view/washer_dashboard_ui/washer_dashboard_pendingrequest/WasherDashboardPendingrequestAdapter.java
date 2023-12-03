package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardPendingrequestAdapter extends RecyclerView.Adapter<WasherDashboardPendingrequestAdapter.OrdersViewHolder> {

    private List<Phase1Order> orders;

    public WasherDashboardPendingrequestAdapter(List<Phase1Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_history, parent, false);
        return new OrdersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID());
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewCourierStatus.setText("Courier Status: " + order.getCourierStatus());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void washerMarkReceivedFromCourier(){

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

