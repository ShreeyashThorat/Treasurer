package com.shree.treasurer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Personal_Details extends AppCompatActivity {
    TextInputEditText pan_no,address_one, address_two, address_three, pincode, alternate_mo;
    RadioGroup radioGroup, radiogroup_one;
    RadioButton radiobutton_gender, radiobutton_employment;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressBar progressBar_submit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Personal Details");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);

        Button submit = findViewById(R.id.submit_personal_data);
        TextInputEditText date_picker = findViewById(R.id.date_of_birth_pick);
        progressBar_submit = findViewById(R.id.progressbar_personal_data_submit);
        pan_no = findViewById(R.id.pan_number);
        address_one = findViewById(R.id.add_line_one);
        address_two = findViewById(R.id.add_line_two);
        address_three = findViewById(R.id.add_line_three);
        pincode = findViewById(R.id.pin_code);
        alternate_mo = findViewById(R.id.alternate_phone);
        radioGroup = findViewById(R.id.gender);
        radiogroup_one = findViewById(R.id.employment);
        date_picker.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Personal_Details.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our edit text.
                        date_picker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    year, month, day);
            datePickerDialog.show();
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radiogroup_ = radioGroup.getCheckedRadioButtonId();
                radiobutton_gender = findViewById(radiogroup_);

                int radiogroup_one_ = radiogroup_one.getCheckedRadioButtonId();
                radiobutton_employment = findViewById(radiogroup_one_);

                String pan_no_ = Objects.requireNonNull(pan_no.getText()).toString().trim();
                String dob = Objects.requireNonNull(date_picker.getText()).toString().trim();
                String address_one_ = Objects.requireNonNull(address_one.getText()).toString().trim();
                String address_two_ = Objects.requireNonNull(address_two.getText()).toString().trim();
                String address_three_ = Objects.requireNonNull(address_three.getText()).toString().trim();
                String pincode_ = Objects.requireNonNull(pincode.getText()).toString().trim();
                String alternate_mo_ = Objects.requireNonNull(alternate_mo.getText()).toString().trim();

                if(pan_no_.isEmpty() && dob.isEmpty() && address_one_.isEmpty() && address_two_.isEmpty() && address_three_.isEmpty() && pincode_.isEmpty() && alternate_mo_.isEmpty()){
                    Toast.makeText(Personal_Details.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar_submit.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Users");

                    Query query = reference.orderByChild("email").equalTo(firebaseUser.getEmail());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                String pan_no__ = pan_no.getText().toString().trim();
                                String dob_ = date_picker.getText().toString().trim();
                                String address_= address_one.getText().toString() + " "
                                        + address_two.getText().toString() + " "
                                        + address_three.getText().toString() + " "
                                        + pincode.getText().toString();
                                String _alternate_mo_ = alternate_mo.getText().toString().trim();
                                String _gender_ = radiobutton_gender.getText().toString();
                                String _employment_ = radiobutton_employment.getText().toString();

                                storingdata storingdata = new storingdata(pan_no__, dob_, _gender_, _employment_, address_, _alternate_mo_);
                                reference.child(firebaseUser.getUid()).child("personalinfo").setValue(storingdata);
                                sendotp();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            private void sendotp() {
                Query query_email = reference.orderByChild("email").equalTo(firebaseUser.getEmail());
                query_email.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            String phone_no= String.valueOf(dataSnapshot1.child("phone_no").getValue());
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + phone_no,
                                    60,
                                    TimeUnit.SECONDS,
                                    Personal_Details.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            Toast.makeText(Personal_Details.this, "Error : Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            super.onCodeSent(s, forceResendingToken);
                                            Intent intent = new Intent(Personal_Details.this, Verify_otp.class);
                                            intent.putExtra("phoneno",phone_no);
                                            intent.putExtra("backenotp",s);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}