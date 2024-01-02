package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardPendingrequestAdapter extends RecyclerView.Adapter<WasherDashboardPendingrequestAdapter.OrdersViewHolder> {

    private List<Phase1Order> orders;
    private Context context;

    public WasherDashboardPendingrequestAdapter(List<Phase1Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_pendingdeliveryrequest, parent, false);
        return new OrdersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed


        switch (order.getPhase1OrderStatus()) {
            case 0:
                // Pending status
                setPendingStatusStyle(holder,order);
                break;
            case 1:
                // In Rider on the way to Customer
                setRiderOnToCustomerStyle(holder,order);
                break;
            case 2:
                // Rider Receive the clothes
                setRiderReceiveClothesStyle(holder,order);
                break;
            case 3:
                // Rider is on the way
                setRiderOnToWasherStyle(holder,order);
                break;
        }

    }
    private void initialize(OrdersViewHolder holder, Phase1Order order){
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID() +"-- Order Status: " + order.getPhase1OrderStatus()  );
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
    }
    private void setPendingStatusStyle(OrdersViewHolder holder,Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_customer_waiting_for_approval));
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestRequestConfirmation.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });
    }

    private void setRiderOnToCustomerStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_to_customer));
        holder.textViewCourierStatus.setText("Rider on the way to Customer");
    }

    private void setRiderReceiveClothesStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_to_customer));
        holder.textViewCourierStatus.setText("Rider Receive the Clothes");
    }

    private void setRiderOnToWasherStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_here));
        holder.textViewCourierStatus.setText("Rider is on the way");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestReceivedClothes.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });

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

