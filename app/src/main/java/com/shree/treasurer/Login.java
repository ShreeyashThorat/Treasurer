package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    Button login;
    TextView forgot_password, create_account ,sign_up;
    TextInputEditText email, password;
    FirebaseUser currentUser;
    private FirebaseAuth fAuth;
    ProgressBar progressbar_login;
    ProgressDialog pd;

    @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ActionBar actionBar=getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action__bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action__bar_text);
        textviewTitle.setText("LOGIN");
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
        forgot_password = findViewById(R.id.forgot_pass);
        create_account = findViewById(R.id.create_account);
        sign_up = findViewById(R.id.sign_up);
        email = findViewById(R.id.email_text);
        password = findViewById(R.id.pass_text);
        fAuth = FirebaseAuth.getInstance();
        progressbar_login = findViewById(R.id.progressbar_login);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        login = findViewById(R.id.login_btn);
        if (fAuth != null) {
            currentUser = fAuth.getCurrentUser();
        }
        create_account.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });
        sign_up.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill= Objects.requireNonNull(email.getText()).toString().trim();
                String pass = Objects.requireNonNull(password.getText()).toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                }else {
                    progressbar_login.setVisibility(View.VISIBLE);
                    login.setVisibility(View.INVISIBLE);
                    loginUser(emaill, pass);
                }
            }

            private void loginUser(String emaill, String pass) {
                fAuth.signInWithEmailAndPassword(emaill, pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressbar_login.setVisibility(View.VISIBLE);
                        login.setVisibility(View.INVISIBLE);
                        FirebaseUser user = fAuth.getCurrentUser();
                        if(Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser()){
                            assert user != null;
                            String email = user.getEmail();
                            String uid = user.getUid();
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", "");
                            hashMap.put("phone", "");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            // store the value in Database in "Users" Node
                            DatabaseReference reference = database.getReference("Users");

                            // storing the value in Firebase
                            reference.child(uid).setValue(hashMap);
                        }
                        Toast.makeText(Login.this, "Logged in Successfully " + Objects.requireNonNull(user).getEmail(), Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(Login.this, Dashboard.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();

                    } else {
                        progressbar_login.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(e -> {
                    progressbar_login.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Error Occurred", Toast.LENGTH_LONG).show();
                });
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Reset your password");
                showPasswordChangeDailog();
            }

            private void showPasswordChangeDailog() {
                @SuppressLint("InflateParams") View view = LayoutInflater.from(Login.this).inflate(R.layout.reset_password, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                final EditText reset_email = view.findViewById(R.id.reset_with_email);
                Button reset_pass = view.findViewById(R.id.reset_password);
                reset_pass.setOnClickListener(view1 -> {
                    String email = reset_email.getText().toString().trim();
                    if (email.isEmpty()){
                        Toast.makeText(getApplication(),"Enter your registered email id",Toast.LENGTH_SHORT).show();
                    }
                    else {
                       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                       DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

                        final String email_one = reset_email.getText().toString();

                        Query check_user = databaseReference.orderByChild("email").equalTo(email_one);
                        check_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    reset_email.setError(null);
                                    reset_email.setFocusable(false);

                                    fAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            pd.dismiss();
                                            Toast.makeText(Login.this, "We have sent you instructions to reset your password on your provided email i'd",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            dialog.dismiss();
                                            Toast.makeText(Login.this,"Failed to send reset email!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else {
                                    reset_email.setError("No such user exist!");
                                    reset_email.setFocusable(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}