package com.shree.treasurer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    Button register;
    TextView already_have_account, sign_in;
    TextInputEditText fname, femail, fphone, fpassword,f_confirmpass;
    ProgressBar progressBar_register;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @SuppressLint({"SetTextI18n", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final ActionBar actionBar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action__bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action__bar_text);
        textviewTitle.setText("Register");
        assert actionBar != null;
        actionBar.setCustomView(viewActionBar, params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.RoyalBlue));
        }
        progressBar_register = findViewById(R.id.progressbar_register);
        fname = findViewById(R.id.name);
        femail = findViewById(R.id.email_id);
        fphone =findViewById(R.id.phone_number);
        fpassword = findViewById(R.id.password_txt);
        f_confirmpass = findViewById(R.id.confirm_password_txt);
        register = findViewById(R.id.register_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        already_have_account = findViewById(R.id.already_have_account);
        sign_in = findViewById(R.id.sign_in);
        already_have_account.setOnClickListener(view -> {
            Intent i = new Intent(Register.this, Login.class);
            startActivity(i);
            finish();
        });
        sign_in.setOnClickListener(view -> {
            Intent i = new Intent(Register.this, Login.class);
            startActivity(i);
            finish();
        });
        register.setOnClickListener(view -> {
            String full_name = Objects.requireNonNull(fname.getText()).toString().trim();
            String email_id = Objects.requireNonNull(femail.getText()).toString().trim();
            String phone_no = Objects.requireNonNull(fphone.getText()).toString().trim();
            String pass = Objects.requireNonNull(fpassword.getText()).toString().trim();
            String confirmpass = Objects.requireNonNull(f_confirmpass.getText()).toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                femail.setError("Invalid Email");
                femail.setFocusable(true);
            } else if (full_name.isEmpty()) {
                fname.setError("Please enter your name");
                fname.setFocusable(true);
            } else if (phone_no.length() != 10) {
                fphone.setError("Please enter a valid contact no");
            } else if (pass.length() < 8) {
                fpassword.setError("Length Must be greater than 8 character");
            } else if (!pass.equals(confirmpass)){
                f_confirmpass.setError("Password does not match");
            } else {
                progressBar_register.setVisibility(View.VISIBLE);
                register.setVisibility(View.INVISIBLE);
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("Users");

                Query check_email = reference.orderByChild("email").equalTo(femail.getText().toString());
                check_email.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            progressBar_register.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                            femail.setError("Email already exist. Please choose a different one");
                            femail.setFocusable(true);
                        }else {
                            Query check_mobile = reference.orderByChild("phone_no").equalTo(fphone.getText().toString());
                            check_mobile.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        progressBar_register.setVisibility(View.GONE);
                                        register.setVisibility(View.VISIBLE);
                                        fphone.setError("This mobile number already exist. Please choose a different one");
                                        fphone.setFocusable(true);
                                    }else{
                                        registerUser(full_name, email_id, phone_no, pass);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressBar_register.setVisibility(View.GONE);
                                    register.setVisibility(View.VISIBLE);
                                    Toast.makeText(Register.this, "failed", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar_register.setVisibility(View.GONE);
                        register.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
    }

    private void registerUser(String full_name, String email_id, String phone_no, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email_id, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                assert user != null;
                String email_id1 = user.getEmail();
                String uid = user.getUid();
                HashMap<Object, String> hashMap = new HashMap<>();
                hashMap.put("email", email_id1);
                hashMap.put("uid", uid);
                hashMap.put("phone_no", phone_no);
                hashMap.put("name", full_name);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");
                reference.child(uid).setValue(hashMap);
                Intent mainIntent = new Intent(Register.this, Personal_Details.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            } else {
                Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(Register.this, "Error Occurred", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}