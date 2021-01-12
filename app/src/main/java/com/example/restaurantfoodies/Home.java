package com.example.restaurantfoodies;


import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import com.example.restaurantfoodies.Common.Common;
import com.example.restaurantfoodies.Interface.ItemClickListner;
import com.example.restaurantfoodies.Model.Category;
import com.example.restaurantfoodies.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;


import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;

import android.widget.TextView;



public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer_layout;
    ActionBarDrawerToggle mToggle;


    FirebaseDatabase database;
    DatabaseReference category;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri newUri;
    TextView txtFullName;
    RecyclerView recycler_menu;



    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        //init Firebase

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, OrderRequests.class);
                startActivity(intent);

            }
        });

        drawer_layout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentRestaurant.getRestaurantName());
//}
        //load menu
//        dotsForMenu = (ImageView) findViewById(R.id.dotsForMenu);
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new GridLayoutManager(this, 3));

//        adDodts();

        loadMenu();

    }





    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.exit, null);
            Button btnExitYes = view.findViewById(R.id.btnExitYes);
//            Button btnExitNo = view.findViewById(R.id.btnExitNo);
            btnExitYes.setOnClickListener(new View.OnClickListener() {
                //                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }
            });

            AlertDialog.Builder alertDialogBox = new AlertDialog.Builder(this);
            alertDialogBox.setMessage("Are you sure you want to exit from application?");
            alertDialogBox.setView(view);


            alertDialogBox.show();
        }
    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.txtMenuName.setText(model.getName());
//                viewHolder.timing.setText(model.getTiming());
                //Picasso.get().load(model.getTimeing());
                Picasso.get().load(model.getImage()).into(viewHolder.imageView);
//                final Category clickItem = model;
                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(Home.this,FoodList.class);


                        //because category id is key, so we get key of this item
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);

//            }
//        });
                    }
                });

            }
        };
        //refresh if data change
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    //@override
    public boolean onOptionsItemSelected(MenuItem item) {

        // if(item.getItemId()==R.id.menu_search)
        //startActivity(new Intent(Home.this, SearchActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("Statement with Empty body")

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference table_user = database.getReference("User");
        int id = menuItem.getItemId();

        if  (id == R.id.complains) {

            Intent intent = new Intent(Home.this, Complains.class);
            startActivity(intent);

//        }  else if (id == R.id.suggestion) {
//
////            Intent cartIntent = new Intent(Home.this, Suggestions.class);
////            startActivity(cartIntent);
//
//
        }        else if (id == R.id.nav_order) {

            Intent orderIntent = new Intent(Home.this, OrderRequests.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {


            Intent signOut = new Intent(Home.this, MainActivity.class);
            signOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(signOut);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
