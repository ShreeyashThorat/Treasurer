package com.shree.treasurer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.MessageFormat;

public class Emi_calculator extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5;
    EditText et1, et2, et3;
    int res;
    int n;
    int res1;
    int res2;
    float f;
    double res3;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calculator);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("EMI Calculator");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.estimated_emi);
        t3 = findViewById(R.id.principal_amt);
        t4 = findViewById(R.id.interest_amt);
        t5 = findViewById(R.id.amt_pay);
        et1 = findViewById(R.id.loan_amt);
        et2 = findViewById(R.id.loan_duration);
        et3 = findViewById(R.id.interest);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("")) {
                    int p = Integer.parseInt(et1.getText().toString());
                    int a = Integer.parseInt(et2.getText().toString());
                    double r = Double.parseDouble(et3.getText().toString());

                    if (!(p >= 10000)) {
                        et1.setError("Minimum amount will be at least 10000");
                        et1.setFocusable(true);
                    } else {
                        n = a * 12;
                        f = (float) (r * 0.01) / 12;
                        res = (int) ((p * f * (Math.pow((1 + f), n))) / ((Math.pow((1 + f), n)) - 1));
                        t2.setText(MessageFormat.format("₹{0}", res));
                        t3.setText(MessageFormat.format("₹{0}", p));
                        res1 = res * n;
                        t5.setText(MessageFormat.format("₹{0}", res1));
                        res2 = res1 - p;
                        t4.setText(MessageFormat.format("₹{0}", res2));
                        t1.setText(MessageFormat.format("Loan amount ₹{0} for {1} years and rate {2}% yearly, Your estimated EMI would be", p, a, r));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et1.addTextChangedListener(textWatcher);
        et2.addTextChangedListener(textWatcher);
        et3.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}