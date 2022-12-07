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

public class marriage_calculator extends AppCompatActivity {
    TextView t1, t2, t3;
    EditText et1, et2, et3, et4, et5;
    int n, n1;
    int res, res1, res2;
    double f1, f2, f3;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marriage_calculator);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Marriage Calculator");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        t1 = findViewById(R.id.amount_required);
        t2 = findViewById(R.id.one_time);
        t3 = findViewById(R.id.monthly);
        et1 = findViewById(R.id.child_age);
        et2 = findViewById(R.id.wedding_age);
        et3 = findViewById(R.id.amount_required_as_today);
        et4 = findViewById(R.id.expected_rateof_return);
        et5 = findViewById(R.id.expected_inflation_rate);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("") && !et4.getText().toString().equals("") && !et5.getText().toString().equals("")) {

                    int a = Integer.parseInt(et1.getText().toString());
                    int b = Integer.parseInt(et2.getText().toString());
                    int p = Integer.parseInt(et3.getText().toString());
                    double e = Double.parseDouble(et4.getText().toString());
                    double r = Double.parseDouble(et5.getText().toString());
                    if (!(p >= 100000)) {
                        et3.setError("Minimum amount will be at least 1  Lakh");
                        et3.setFocusable(true);
                    }
                    if (!(b > a)) {
                        et2.setError("Child's wedding age must be greater than child's current age");
                        et2.setFocusable(true);
                    } else {
                        n = b - a;
                        n1 = n * 12;
                        f1 = (float) (r * 0.01);
                        f2 = (float) (e * 0.01);
                        f3 = (float) ((e * 0.01) / 12);
                        res = (int) (p * (Math.pow((1 + f1), n)));
                        t1.setText(MessageFormat.format("₹{0}", res));
                        res1 = (int) (res / (Math.pow((1 + f2), n)));
                        t2.setText(MessageFormat.format("₹{0}", res1));
                        res2 = (int) ((res * f3) / ((Math.pow((1 + f3), n1) - 1) * (1 + f3)));
                        t3.setText(MessageFormat.format("₹{0}", res2));
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
        et5.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}