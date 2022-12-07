package com.shree.treasurer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_otp extends AppCompatActivity {
    TextView get__contact, otp_resend;
    Button verify_otp;
    EditText ed1,ed2,ed3,ed4,ed5,ed6;
    String getotp;
    ProgressBar progressBar_verify;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Verify your account");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        ed1 = findViewById(R.id.editTextNumber);
        ed2 = findViewById(R.id.editTextNumber2);
        ed3 = findViewById(R.id.editTextNumber3);
        ed4 = findViewById(R.id.editTextNumber4);
        ed5 = findViewById(R.id.editTextNumber5);
        ed6 = findViewById(R.id.editTextNumber6);
        progressBar_verify = findViewById(R.id.progressbar_verify_otp);
        get__contact = findViewById(R.id.get_contact);
        get__contact.setText(String.format(
                "+91-%s", getIntent().getStringExtra("phoneno")
        ));
        getotp = getIntent().getStringExtra("backenotp");
        verify_otp = findViewById(R.id.verify_otp_btn);
        verify_otp.setOnClickListener(view -> {
            String ed_1 = ed1.getText().toString().trim();
            String ed_2 = ed2.getText().toString().trim();
            String ed_3 = ed3.getText().toString().trim();
            String ed_4 = ed4.getText().toString().trim();
            String ed_5 = ed5.getText().toString().trim();
            String ed_6 = ed6.getText().toString().trim();

            if(!ed_1.isEmpty() && !ed_2.isEmpty() && !ed_3.isEmpty() && !ed_4.isEmpty() && !ed_5.isEmpty() && !ed_6.isEmpty()){
                String edittext = ed1.getText().toString() +
                        ed2.getText().toString() +
                        ed3.getText().toString() +
                        ed4.getText().toString() +
                        ed5.getText().toString() +
                        ed6.getText().toString();
                if(getotp != null){

                    progressBar_verify.setVisibility(View.VISIBLE);
                    verify_otp.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotp, edittext);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(task -> {
                                progressBar_verify.setVisibility(View.GONE);
                                verify_otp.setVisibility(View.VISIBLE);
                                if(task.isSuccessful()){
                                    Toast.makeText(Verify_otp.this, "OTP successfully verified", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Verify_otp.this, Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }else {
                    Toast.makeText(Verify_otp.this, "Please check your OTP",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Verify_otp.this,"Please enter valid otp",Toast.LENGTH_SHORT).show();
            }
        });
        movenumber();
        otp_resend = findViewById(R.id.resend_otp);
        otp_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("phoneno"),
                        60,
                        TimeUnit.SECONDS,
                        Verify_otp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(Verify_otp.this, "Error : Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String new_s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(new_s, forceResendingToken);
                                getotp = new_s;
                                Toast.makeText(Verify_otp.this,"OTP has resend Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void movenumber() {
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    ed2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    ed3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    ed4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    ed5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    ed6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}