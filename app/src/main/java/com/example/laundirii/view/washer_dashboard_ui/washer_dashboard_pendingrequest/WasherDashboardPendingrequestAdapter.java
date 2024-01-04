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
import com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes.WasherDashboardPendingrequestClothesReadyToPickUp;
import com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes.WasherDashboardPendingrequestReceivedClothes;

import java.util.List;

public class WasherDashboardPendingrequestAdapter extends RecyclerView.Adapter<WasherDashboardPendingrequestAdapter.OrdersViewHolder> {

    private List<Phase1Order> orders;
    private Context context;
    //private Date currentDate;

    public WasherDashboardPendingrequestAdapter(List<Phase1Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_pendingdeliveryrequest, parent, false);
        //currentDate = new Date();
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
                //TODO
                // Make the logic,
                setRiderOnToWasherStyle(holder,order);
                break;
        }

    }
    private void initialize(OrdersViewHolder holder, Phase1Order order){
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID() +"-- Order Status: " + order.getPhase1OrderStatus()  );
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
    }

    private void setClothesReadyToPickUpStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_customer_waiting_for_approval));
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestClothesReadyToPickUp.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });

    }

    private void setWasherWeightClothesStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_customer_waiting_for_approval));
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestReceivedClothes.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });
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

//    private boolean isOutOfTimer(Phase1Order phase1Order) {
//        try {
//            // Parse the date string to a Date object
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            Date datePlaced = sdf.parse(phase1Order.getDatePlaced());
//
//            // Calculate the difference in milliseconds
//            long differenceInMillis = currentDate.getTime() - datePlaced.getTime();
//
//            // Convert the difference to minutes
//            long differenceInMinutes = differenceInMillis / (60 * 1000);
//
//            // Check if the difference is greater than 5 minutes
//            boolean b = differenceInMinutes >= 1;
//            Log.e("isOutOfTimer", b + "" + "\ndateplaced: " + datePlaced.getTime() + "\ncurrent: " + currentDate.getTime() + "\ndiff: " + differenceInMinutes);
//            return differenceInMinutes >= 1;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setData(List<Phase1Order> newOrders) {
        this.orders = newOrders;
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

