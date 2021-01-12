package com.example.restaurantfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantfoodies.Common.Common;
import com.example.restaurantfoodies.Interface.ItemClickListner;
import com.example.restaurantfoodies.Model.Food;
import com.example.restaurantfoodies.Model.Order;
import com.example.restaurantfoodies.Model.Request;
import com.example.restaurantfoodies.ViewHolder.FoodViewHolder;
import com.example.restaurantfoodies.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class OrderRequests extends AppCompatActivity {
    static CountDownTimer timer = null;
    //    final CountDownTimer finalTimer = timer;
    static char a;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter1;

    FirebaseDatabase database;
    DatabaseReference requests, foods;
    String counter = "";
    int minute;
    long min;
    TextView tv_timer;
    static TextView tv_time;
    Food food;
    Order order;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_requests);
//        min = 30 * 60 * 1000;
        //For Counter
//        tv_time = findViewById(R.id.timeS);


        //firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

//        counter = getIntent().getStringExtra("Counter");
//        if (counter != null && !counter.isEmpty()) {
//            if (timer != null) {
//                timer.cancel();
//                counterForOrder(min);
//            } else {
//                counterForOrder(min);
//            }
//            tv_time.setVisibility(View.VISIBLE);
////            timer.cancel();
//            Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
//        }


        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);

//        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        if (Common.IMEI != null && Common.currentUser==null) {
//            loadOrdersGuest(Common.IMEI);
//
//        } else {
        try {
            loadOrders(Common.currentRestaurant.getRestaurantId());
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

//        }

    }

//    private void loadOrdersGuest(String IMEI) {
//
//        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
//                Request.class,
//                R.layout.order_layout,
//                OrderViewHolder.class,
//                requests.orderByChild("imei_R").equalTo(Common.IMEI)
//        ) {
//            @Override
//            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {
//
//                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
//                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
////                viewHolder.txtOrderStatus.setTextColor();
//
//                viewHolder.txtOrderAddress.setText(model.getAddress());
//                viewHolder.txtOrderPhone.setText(model.getPhone());
////                String.valueOf(model.getTimeStart());
////                viewHolder.timeStart.setText( String.valueOf(model.getTimeStart()));
//
//                viewHolder.setItemClickListner(new ItemClickListner() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
////                        Intent intent = new Intent(OrderStatus.this, OrderTracker.class);
//
////                        Common.currentRequest = model;
//                        //because category id is key, so we get key of this item
////                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
////                        startActivity(intent);
//                    }
//                });
//            }
//        };
//        adapter.notifyDataSetChanged();
//        recyclerView.setAdapter(adapter);
//    }

    private void loadOrders(String id) {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("restaurant").equalTo(id)
//                        .orderByChild("restaurant").equalTo(Common.currentRestaurant.getRestaurantName()

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
//                viewHolder.food_name.setText(model.getName());
//                viewHolder.restaurant_timing.setText(model.getTiming());
//                Picasso.get().load(model.getImage()).into(viewHolder.food_image);


                viewHolder.order_id.setText("Order Number : " + adapter.getRef(position).getKey());
                viewHolder.order_status.setText("Order Status : " + convertCodeToStatus(model.getStatus()));
//                viewHolder.txtOrderStatus.setTextColor();

                viewHolder.order_phone.setText("Order Phone : " + model.getPhone());
                viewHolder.order_address.setText("Order Address : " + model.getAddress());
                viewHolder.requestTotal.setText("Total : " + model.getTotal());

//                foods = database.getReference(adapter.getRef(position).getKey()).child("foods");
//                Food food = new Food();
//                viewHolder.order_food_name.setText(model.getName());
//                viewHolder.order_food_quantity.setText(model.get());
//foods.

//                String.valueOf(model.getTimeStart());
//                viewHolder.timeStart.setText( String.valueOf(model.getTimeStart()));

                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, final int position, boolean isLongClick) {
//                        foods = database.getReference(adapter.getRef(position).getKey()).child("foods");
//
//                        adapter1 = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
//                                Food.class,
//                                R.layout.order_layout,
//                                FoodViewHolder.class,
//                                requests.child(adapter.getRef(position).getKey()).child("foods")
////                        .orderByChild("restaurant").equalTo(Common.currentRestaurant.getRestaurantName()
//
//                        ) {
//                            @Override
//                            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderRequests.this);
                        alertDialog.setTitle("Order Details");
                        LayoutInflater layoutInflater = getLayoutInflater();
                        View view1 = layoutInflater.inflate(R.layout.food_request, null);

                        final TextView requestName;
                        final TextView requestQuantity;

                        requestName = (TextView) view1.findViewById(R.id.food_name);
                        requestQuantity = (TextView) view1.findViewById(R.id.food_quantity);

                        requests.child(adapter.getRef(position).getKey()).child("foods")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        food = dataSnapshot.getValue(Food.class);
                                        order = dataSnapshot.getValue(Order.class);
                                        requestName.setText("Name : " + order.getProductName());
                                        requestQuantity.setText("Quantity : " + order.getQuantity());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
//                        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requests.child(adapter.getRef(position).getKey()).child("foods")
//                                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                                request = new Request("3");
////                                                requests.child(adapter.getRef(position).getKey()).child("foods").child("status").setValue(request);
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//                            }
//                        }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requests.child(adapter.getRef(position).getKey()).removeValue();
//                            }
//                        });

                        alertDialog.setView(view1);

                        alertDialog.show();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public static String convertCodeToStatus(String status) {

        if (status.equals("0"))
            return "confirmed";

        else if (status.equals("1"))
            return "Deny";

        else if (status.equals("2"))
            return "Placed";

        else if (status.equals("3"))
            return "On the Way";

        else if (status.equals("4"))
            return "Canceled";

        else
            return "Shipped";
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE)) {
//            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
            updateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
            Toast.makeText(this, "" + adapter.getItem(item.getOrder()), Toast.LENGTH_SHORT).show();
//            updateFood(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
            // we are reuse AddNewMenuItem Class for update menu item
//
//            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
////            intent.putExtra("update", Common.UPDATE);
//            intent.putExtra("key", adapter.getRef(item.getOrder()).getKey());
//            intent.putExtra("OrderName", (Parcelable) adapter.getItem(item.getOrder()));
        } else {
            delete(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void delete(String key) {
        requests.child(key).removeValue();
    }

    private void updateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(OrderRequests.this);
        alertDialog1.setTitle("Update");
        alertDialog1.setMessage("Select a Option");
        LayoutInflater layoutInflater = getLayoutInflater();
        View viewOrderUpdate = layoutInflater.inflate(R.layout.order_update, null);
        final MaterialSpinner spinnerForOrder = (MaterialSpinner) viewOrderUpdate.findViewById(R.id.spinnerForOrder);
        spinnerForOrder.setItems("Confirmed", "Deny", "Placed", "On The Way", "Delivered");
        alertDialog1.setView(viewOrderUpdate);
        final String currentKey = key;
        alertDialog1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinnerForOrder.getSelectedIndex()));
                requests.child(currentKey).setValue(item);
            }
        });
        alertDialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog1.show();

    }
//    private static void counterForOrder(long min) {
//
//
//        timer = new CountDownTimer(min, 1000) {
//            public void onTick(long millisUntilFinished) {
//                int seconds = (int) (millisUntilFinished / 1000) % 60;
//                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
//                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
//                tv_time.setText(String.format("Last Order" + "%d:%d:%d", hours, minutes, seconds));
//            }
//
//            public void onFinish() {
//
////                Toast.makeText(, "Your time has been completed",
////                        Toast.LENGTH_LONG).show();
////                finalTimer.cancel();
//            }
//
//        };
//
//        timer.start();
//    }
}
