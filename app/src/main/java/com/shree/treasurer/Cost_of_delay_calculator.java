package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

public class Cost_of_delay_calculator extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7;
    EditText et1, et2, et3, et4;
    int res;
    int n, n1;
    int res1;
    int res2;
    int res4;
    int res5;
    float f;
    double res3;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_of_delay_calculator);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Cost of Delay");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        t1 = findViewById(R.id.maturity_value1);
        t2 = findViewById(R.id.amount_invested);
        t3 = findViewById(R.id.gained_amount);
        t4 = findViewById(R.id.delay_maturity_value);
        t5 = findViewById(R.id.delay_maturity_value1);
        t6 = findViewById(R.id.invested_amount);
        t7 = findViewById(R.id.gained_amt);
        et1 = findViewById(R.id.monthly_invest);
        et2 = findViewById(R.id.investment_period);
        et3 = findViewById(R.id.annual_returns);
        et4 = findViewById(R.id.delay_from_today);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("") && !et4.getText().toString().equals("")) {
                    int p = Integer.parseInt(et1.getText().toString());
                    int a = Integer.parseInt(et2.getText().toString());
                    double r = Double.parseDouble(et3.getText().toString());
                    int b = Integer.parseInt(et4.getText().toString());

                    if (!(p >= 500)) {
                        et1.setError("Minimum amount will be at least 500");
                        et1.setFocusable(true);
                    } else {
                        n = a * 12;
                        f = (float) (r * 0.01) / 12;
                        res = (int) (p * (((Math.pow((1 + f), n) - 1) / f) * (1 + f)));
                        t1.setText(MessageFormat.format("₹{0}", res));
                        res1 = p * n;
                        t2.setText(MessageFormat.format("₹{0}", res1));
                        res2 = res - res1;
                        t3.setText(MessageFormat.format("₹{0}", res2));
                        n1 = n - b;
                        res3 = (int) (p * (((Math.pow((1 + f), n1) - 1) / f) * (1 + f)));
                        t5.setText(MessageFormat.format("{0}", res3));
                        res4 = p * n1;
                        t6.setText(MessageFormat.format("₹{0}", res4));
                        res5 = (int) (res3 - res4);
                        t7.setText(MessageFormat.format("₹{0}", res5));
                        t4.setText(MessageFormat.format("If you start SIP with delay of {0} months then your Maturity value would be", b));
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
        et4.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}