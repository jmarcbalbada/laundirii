package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;

import java.util.List;

public class WasherDashboardHistoryAdapter extends RecyclerView.Adapter<WasherDashboardHistoryAdapter.PedingViewHolder> {
    private List<Orders> orders;
    private DashboardController dashboardController;
    Context context;

    public WasherDashboardHistoryAdapter(List<Orders> orders,Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public WasherDashboardHistoryAdapter.PedingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_history, parent, false);
        dashboardController = new DashboardController();
        return new PedingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasherDashboardHistoryAdapter.PedingViewHolder holder, int position) {
        Orders order = orders.get(position);
        // if the history is Phase 1

        if(order instanceof Phase1Order){
            Phase1Order phase1Order = ((Phase1Order)order);
            holder.textViewOrderId.setText("");
            holder.textViewClientName.setText("Client Name: " + ((Phase1Order) order).getClient().getName());
            holder.textViewCourierName.setText("Courier Name: " + ((Phase1Order) order).getCourier().getName());
            holder.textViewLaundryWeight.setText("Laundry Weight: " + ((Phase1Order) order).getInitialLoad() +" KG");
            holder.textViewDatePlaced.setText("Date Placed: " + ((Phase1Order) order).getDatePlaced());
            if(phase1Order.getPhase1OrderStatus() == -1 ){
                holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
                holder.textViewStatus.setText("Process: Declined Client to Washer");
            }else{
                holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending9));
                holder.textViewStatus.setText("Process: Completed Client to Washer");
            }
        }
        // if the history is Phase 2
        if(order instanceof Phase2Order){
            Phase2Order phase2Order = ((Phase2Order)order);
            holder.textViewOrderId.setText("");
            holder.textViewClientName.setText("Client Name: " + ((Phase2Order) order).getClient().getName());
            holder.textViewCourierName.setText("Courier Name: " +((Phase2Order) order).getCourier().getName()+"" );
            holder.textViewDatePlaced.setText("Date Placed: " + ((Phase2Order) order).getDatePlaced());


            holder.textViewStatus.setText("Status: " + phase2Order.getPhase2OrderStatus());
            if(phase2Order.getPhase2OrderStatus() == -1 ){
                holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
                holder.textViewStatus.setText(" Process: Declined Washer to Client");
                holder.textViewLaundryWeight.setText("");
            }else{
                int laundryweight = dashboardController.getPhase1LaundryWeight(phase2Order.getPhase2_phase1OrderID(),context);
                holder.textViewLaundryWeight.setText("Laundry Weight: " + laundryweight+" KG");
                holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending9));
                holder.textViewStatus.setText(" Process: Completed Washer to Client");
            }
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class PedingViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewStatus;
        TextView textViewCourierName;
        TextView textViewLaundryWeight;
        TextView textViewDatePlaced;
        public PedingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewCourierName = itemView.findViewById(R.id.textViewCourierName);
            textViewLaundryWeight = itemView.findViewById(R.id.textViewLaundryWeight);
            textViewDatePlaced = itemView.findViewById(R.id.textViewLaundryDatePlaced);
            // Initialize other TextViews or UI components as needed
        }
    }
}
