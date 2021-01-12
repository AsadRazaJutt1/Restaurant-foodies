package com.example.restaurantfoodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;


import com.example.restaurantfoodies.Common.Common;
import com.example.restaurantfoodies.Interface.ItemClickListner;
import com.example.restaurantfoodies.Model.Getter_Setter_Compains;
import com.example.restaurantfoodies.ViewHolder.ComplainViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complains extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Getter_Setter_Compains, ComplainViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complains);
        database = FirebaseDatabase.getInstance();
        complain = database.getReference("Complaints");


        recyclerView = (RecyclerView) findViewById(R.id.complainList);
        recyclerView.setHasFixedSize(true);

//        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Getter_Setter_Compains, ComplainViewHolder>(
                Getter_Setter_Compains.class,
                R.layout.complain_layout,
                ComplainViewHolder.class,
                complain.orderByChild("restaurant").equalTo(Common.currentRestaurant.getRestaurantId())
        ) {
            @Override
            protected void populateViewHolder(ComplainViewHolder viewHolder, final Getter_Setter_Compains model, int position1) {

                viewHolder.order_id.setText(adapter.getRef(position1).getKey());
                viewHolder.order_Complain_Title.setText(model.getTitle());
//                viewHolder.txtOrderStatus.setTextColor();

                viewHolder.order_Complain_Msg.setText(model.getComplaintMsg());
                viewHolder.order_phone.setText(model.getPhone());
//                String.valueOf(model.getTimeStart());
//                viewHolder.timeStart.setText( String.valueOf(model.getTimeStart()));

                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent intent = new Intent(OrderRequests.this, OrderTracker.class);

//                        Common.currentRequest = model;
                        //because category id is key, so we get key of this item
//                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
//                        startActivity(intent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}