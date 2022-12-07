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

public class Retirement_calculator extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9;
    int res, res1, res2, res3, res4, res5, res6, res7;
    int n, n1, n2;
    double d1, d2;
    double g1, g2;
    double h1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement_calculator);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Retirement Calculator");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        t1 = findViewById(R.id.expected_monthly_expense);
        t2 = findViewById(R.id.expected_monthly_expense1);
        t3 = findViewById(R.id.estimated_amt_required1);
        t4 = findViewById(R.id.total_saving_will_be1);
        t5 = findViewById(R.id.shortfall_saving);
        t6 = findViewById(R.id.achieve_goal_with_sip);
        t7 = findViewById(R.id.achieve_goal_with_lumpsum);
        ed1 = findViewById(R.id.current_age);
        ed2 = findViewById(R.id.retirement_age);
        ed3 = findViewById(R.id.current_monthly_expenses);
        ed4 = findViewById(R.id.expected_inflation);
        ed5 = findViewById(R.id.current_saving_per_month);
        ed6 = findViewById(R.id.existing_corpus);
        ed7 = findViewById(R.id.pre_retirement_returns);
        ed8 = findViewById(R.id.post_retirement_returns);
        ed9 = findViewById(R.id.life_expectancy);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!ed1.getText().toString().equals("") && !ed2.getText().toString().equals("") && !ed3.getText().toString().equals("") && !ed4.getText().toString().equals("") && !ed5.getText().toString().equals("") && !ed6.getText().toString().equals("") && !ed7.getText().toString().equals("") && !ed8.getText().toString().equals("") && !ed9.getText().toString().equals("")) {
                    int a = Integer.parseInt(ed1.getText().toString());
                    int b = Integer.parseInt(ed2.getText().toString());
                    int c = Integer.parseInt(ed3.getText().toString());
                    double d = Double.parseDouble(ed4.getText().toString());
                    int e = Integer.parseInt(ed5.getText().toString());
                    int f = Integer.parseInt(ed6.getText().toString());
                    double g = Double.parseDouble(ed7.getText().toString());
                    double h = Double.parseDouble(ed8.getText().toString());
                    int j = Integer.parseInt(ed9.getText().toString());

                    if (!(c >= 1000)) {
                        ed3.setError("Minimum amount will be at least 1,000");
                        ed3.setFocusable(true);
                    }
                    if (!(b > a)) {
                        ed2.setError("Retirement age should be greater than current age");
                        ed2.setFocusable(true);
                    }
                    if (!(j > b)) {
                        ed9.setError("Life Expectancy must be greater than retirement age");
                        ed9.setFocusable(true);
                    } else {

                        d1 = (float) (d * 0.01);
                        n = b - a;
                        res = (int) (c * (Math.pow((1 + d1), n)));
                        t1.setText(MessageFormat.format("Expected Monthly Expenses When you Retire at {0} years would be", b));
                        t2.setText(MessageFormat.format("₹{0} /-", res));
                        h1 = h * 0.01;
                        n1 = (j - b) * 12;
                        d2 = (((1 + h1) / (1 + d1)) - 1) / 12;
                        res1 = (int) ((res * (1 + d2) * ((Math.pow((1 + d2), n1) - 1) / d2)) / Math.pow((1 + d2), n1));
                        t3.setText(MessageFormat.format("₹{0} /-", res1));
                        g1 = (float) (g * 0.01);
                        res2 = (int) (f * (Math.pow((1 + g1), n)));
                        g2 = (float) (g * 0.01) / 12;
                        n2 = n * 12;
                        res3 = (int) (e * (((Math.pow((1 + g2), n2) - 1) / g2) * (1 + g2)));
                        res4 = res2 + res3;
                        t4.setText(MessageFormat.format("₹{0}", res4));
                        res5 = res1 - res4;
                        t5.setText(MessageFormat.format("Shortfall in savings ₹{0}", res5));
                        res6 = (int) (res5 / (((Math.pow((1 + g2), n2) - 1) / g2) * (1 + g2)));
                        t6.setText(MessageFormat.format("₹{0}", res6));
                        res7 = (int) (res5 / ((Math.pow((1 + g1), n))));
                        t7.setText(MessageFormat.format("₹{0}", res7));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        ed1.addTextChangedListener(textWatcher);
        ed2.addTextChangedListener(textWatcher);
        ed3.addTextChangedListener(textWatcher);
        ed4.addTextChangedListener(textWatcher);
        ed5.addTextChangedListener(textWatcher);
        ed6.addTextChangedListener(textWatcher);
        ed7.addTextChangedListener(textWatcher);
        ed8.addTextChangedListener(textWatcher);
        ed9.addTextChangedListener(textWatcher);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}