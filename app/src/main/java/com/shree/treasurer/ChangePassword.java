package com.shree.treasurer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePassword extends AppCompatActivity {
    TextInputEditText new_password, old_password;
    Button change_password;
    FirebaseUser firebaseUser;
    ProgressBar progressbar_pass;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Change password");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        new_password = findViewById(R.id.new_pass);
        old_password = findViewById(R.id.old_pass);
        change_password = findViewById(R.id.change_pass);
        progressbar_pass = findViewById(R.id.progressbar_pass);
        change_password.setOnClickListener(view -> {
            progressbar_pass.setVisibility(View.VISIBLE);
            change_password.setVisibility(View.INVISIBLE);
            String newp = Objects.requireNonNull(new_password.getText()).toString().trim();
            String oldp = Objects.requireNonNull(old_password.getText()).toString().trim();
            if (oldp.isEmpty() && newp.isEmpty()) {
                progressbar_pass.setVisibility(View.GONE);
                change_password.setVisibility(View.VISIBLE);
                Toast.makeText(ChangePassword.this, "Please fill all  fields", Toast.LENGTH_LONG).show();
                return;
            }
            updatePassword(oldp, newp);
        });
    }

    private void updatePassword(String oldp, String newp) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        AuthCredential authCredential= EmailAuthProvider.getCredential(Objects.requireNonNull(Objects.requireNonNull(firebaseUser).getEmail()), oldp);
        firebaseUser.reauthenticate(authCredential)
                .addOnSuccessListener(unused -> {
                    progressbar_pass.setVisibility(View.GONE);
                    change_password.setVisibility(View.VISIBLE);
                    firebaseUser.updatePassword(newp)
                            .addOnSuccessListener(unused1 -> {
                                Toast.makeText(ChangePassword.this, "Changed Password", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ChangePassword.this, Dashboard.class);
                                startActivity(i);
                                finish();
                            }).addOnFailureListener(e -> Toast.makeText(ChangePassword.this, "Failed", Toast.LENGTH_LONG).show());
                }).addOnFailureListener(e -> {
                    progressbar_pass.setVisibility(View.GONE);
                    change_password.setVisibility(View.VISIBLE);
                    Toast.makeText(ChangePassword.this, "Failed", Toast.LENGTH_LONG).show();

                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}