package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    LinearLayout account, change_pass, logout;
    TextView name, account_logo;
    ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        account = view.findViewById(R.id.account);
        name = view.findViewById(R.id.account_name);
        change_pass = view.findViewById(R.id.change_password);
        account_logo = view.findViewById(R.id.account_logo);
        account.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(),Account_details .class);
            startActivity(intent);
        });
        change_pass.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(),ChangePassword .class);
            startActivity(intent);
        });


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Users");

        assert firebaseUser != null;
        Query query = reference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name_ = "" + dataSnapshot1.child("name").getValue();
                    // setting data to our text view
                    name.setText(name_);
                    account_logo.setText(String.valueOf(name_.charAt(0)));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        logout = view.findViewById(R.id.logout);
        pd = new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Logout");
                logoutDailog();
            }

            private void logoutDailog() {
                @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.logout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                TextView no = view.findViewById(R.id.no);
                TextView yes = view.findViewById(R.id.yes);
                no.setOnClickListener(view14 -> {
                    dialog.dismiss();
                    pd.dismiss();
                });
                yes.setOnClickListener(view13 -> {
                    firebaseAuth.signOut();
                    dialog.dismiss();
                    pd.dismiss();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                });
            }
        });

        return view;
    }
}