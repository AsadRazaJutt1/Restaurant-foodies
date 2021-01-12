package com.example.restaurantfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantfoodies.Common.Common;
import com.example.restaurantfoodies.Model.Restaurant_Profile;

import com.example.restaurantfoodies.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        textView = (TextView) findViewById(R.id.forget_password);

        // in it firebase

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Restaurants");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Please wait..");
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user does not exist
                        if (dataSnapshot.child('+'+edtPhone.getText().toString()).exists()) {
                            // get user info
                            mDialog.dismiss();
                            Restaurant_Profile profile = dataSnapshot.child('+'+edtPhone.getText().toString()).getValue(Restaurant_Profile.class);
                            profile.setRestaurantPhone(edtPhone.getText().toString());  //set Phone
//                            Toast.makeText(SignIn.this, "User", Toast.LENGTH_SHORT).show();

                            if (profile.getRestaurantPassword().equals(edtPassword.getText().toString())) {

//                                if (user.getIsStaff().equals("Yes")) {
                                    Intent homeIntent = new Intent(MainActivity.this, Home.class);
                                    Common.currentRestaurant = profile;
                                    startActivity(homeIntent);
                                    finish();
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Sig in With Restaurant Account", Toast.LENGTH_SHORT).show();
//                                    mDialog.dismiss();
//                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong password!!", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Not Exist", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void forget_password(View view) {
        Toast.makeText(this, "Add Soon", Toast.LENGTH_SHORT).show();
    }
}
