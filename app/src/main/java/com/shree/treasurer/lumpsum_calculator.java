package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

public class lumpsum_calculator extends AppCompatActivity {
    TextView t1, t2, t3;
    EditText et1, et2, et3, et4;
    CheckBox mCheckBox;
    int res, res1;
    double r1, f1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lumpsum_calculator);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Lumpsum Calculator");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        t1 = findViewById(R.id.one_time_investment);
        t2 = findViewById(R.id.growth);
        t3 = findViewById(R.id.returns);
        et1 = findViewById(R.id.one_time_invest);
        et2 = findViewById(R.id.invest_period);
        et3 = findViewById(R.id.expected_returns);
        et4 = findViewById(R.id.inflation_rate);
        mCheckBox = findViewById(R.id.inflation_adjust);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("") && !et4.getText().toString().equals("")) {
                    int p = Integer.parseInt(et1.getText().toString());
                    int n = Integer.parseInt(et2.getText().toString());
                    double r = Double.parseDouble(et3.getText().toString());
                    double f = Double.parseDouble(et4.getText().toString());
                    r1 = (float) (r * 0.01);
                    f1 = (float) (f * 0.01);

                    if (!(p >= 500)) {
                        et1.setError("Minimum amount will be at least 500");
                        et1.setFocusable(true);
                    } else {
                        if (mCheckBox.isChecked()) {
                            res = (int) (p * (Math.pow((1 + (r1 - f1)), n)));
                            t3.setText(MessageFormat.format("₹{0}", res));
                        } else {
                            res1 = (int) (p * (Math.pow((1 + r1), n)));
                            t3.setText(MessageFormat.format("₹{0}", res1));

                        }
                        t1.setText(MessageFormat.format("₹{0}", p));
                        t2.setText(MessageFormat.format("for {0} years at {1}% annual return will grow your money to", n, r));
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