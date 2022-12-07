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

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class Future_Charges extends AppCompatActivity {
    TextView t1, t2, t3;
    EditText et1, et2, et3;
    double  stt, exchange, gst, sebi, stamp,res, res1,brokerage, turnover;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_charges);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("F&O Future Charges");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        t1 = findViewById(R.id.turnover);
        t2 = findViewById(R.id.total_charges);
        t3 = findViewById(R.id.profit_loss);
        et1 = findViewById(R.id.buying_value);
        et2 = findViewById(R.id.selling_value);
        et3 = findViewById(R.id.quantity);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("")) {
                    double p = Double.parseDouble(et1.getText().toString());
                    double q = Double.parseDouble(et2.getText().toString());
                    double r = Double.parseDouble(et3.getText().toString());
                    turnover = (int) ((p * r) + (q * r));
                    t1.setText(MessageFormat.format("₹{0}", turnover));
                    brokerage = 40;
                    stt = 0.0001 * (q * r);
                    exchange = 0.00002 * turnover;
                    sebi = 0.000001 * turnover;
                    stamp = 0.00002 * (p * r);
                    gst = 0.18 * (brokerage + exchange + sebi);
                    res = (brokerage + stt+ exchange + sebi + stamp + gst);
                    res =Double.parseDouble(new DecimalFormat("##.##").format(res));
                    t2.setText(MessageFormat.format("₹{0}", res));
                    res1 = ((q * r) - (p * r) - res);
                    res =Double.parseDouble(new DecimalFormat("##.##").format(res));
                    t3.setText(MessageFormat.format("₹{0}", res1));
                    if(res1 < 0) {
                        t3.setTextColor(R.color.red);
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