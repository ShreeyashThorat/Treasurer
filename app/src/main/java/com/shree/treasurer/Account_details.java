package com.shree.treasurer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Account_details extends AppCompatActivity {
    TextView name, mobilenumber, email_id, pan, dateofbirth, sex, employment, addr;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Account Details");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        name = findViewById(R.id.name_as_per_pan);
        mobilenumber = findViewById(R.id.mobile_number);
        email_id = findViewById(R.id.email);
        pan = findViewById(R.id.pan_no);
        dateofbirth = findViewById(R.id.dob);
        sex = findViewById(R.id.gender);
        employment = findViewById(R.id.employment_type);
        addr = findViewById(R.id.address);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");

        Query query = reference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name_ = "" + dataSnapshot1.child("name").getValue();
                    String phoneno= "+91 - " + dataSnapshot1.child("phone_no").getValue();
                    String email_id_ = "" + dataSnapshot1.child("email").getValue();
                    String pan_ = "" + dataSnapshot1.child("personalinfo").child("pan").getValue();
                    String dob_ = "" + dataSnapshot1.child("personalinfo").child("dob").getValue();
                    String sex_ = "" + dataSnapshot1.child("personalinfo").child("gender").getValue();
                    String employment_ = "" + dataSnapshot1.child("personalinfo").child("employment_type").getValue();
                    String address_ = "" + dataSnapshot1.child("personalinfo").child("address").getValue();
                    // setting data to our text view
                    name.setText(name_);
                    mobilenumber.setText(phoneno);
                    email_id.setText(email_id_);
                    pan.setText(pan_);
                    dateofbirth.setText(dob_);
                    sex.setText(sex_);
                    employment.setText(employment_);
                    addr.setText(address_);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}