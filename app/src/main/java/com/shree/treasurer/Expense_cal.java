package com.shree.treasurer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Expense_cal extends AppCompatActivity {
    TextView tvSign;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvEmpty, tvBalance;
    EditText etAmount, etMessage;
    ImageView ivSend;
    boolean positive = true;
    RecyclerView rvTransactions;
    TransactionAdapter adapter;
    ArrayList<TransactionClass> transactionList;

    // On create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_cal);

        initViews();

        loadData();

        setCustomActionBar();

        checkIfEmpty(transactionList.size());

        rvTransactions.setHasFixedSize(true);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this,transactionList);
        rvTransactions.setAdapter(adapter);

        tvSign.setOnClickListener(view -> changeSign());

        // On click Send
        ivSend.setOnClickListener(view -> {

            // Input Validation
            if(etAmount.getText().toString().trim().isEmpty())
            {
                etAmount.setError("Enter Amount!");
                return;
            }
            if(etMessage.getText().toString().isEmpty())
            {
                etMessage.setError("Enter a message!");
                return;
            }
            try {
                double amt = Double.parseDouble(etAmount.getText().toString().trim());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String c_date = sdf.format(new Date());
                // Adding Transaction to recycler View
                sendTransaction(amt,etMessage.getText().toString().trim(),positive, c_date);
                checkIfEmpty(transactionList.size());

                // To update Balance
                setBalance(transactionList);
                etAmount.setText("");
                etMessage.setText("");
            }
            catch (Exception e){
                etAmount.setError("Amount should be integer greater than zero!");
            }
        });
    }

    // To set custom action bar
    private void setCustomActionBar() {
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        @SuppressLint("InflateParams") View v = LayoutInflater.from(this).inflate(R.layout.custom_action_bar,null);

        tvBalance = v.findViewById(R.id.tvBalance);

        setBalance(transactionList);
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // To set Balance along with sign (spent(-) or received(+))
    @SuppressLint("SetTextI18n")
    public static void setBalance(ArrayList<TransactionClass> transactionList){
        double bal = calculateBalance(transactionList);
        if(bal<0)
        {
            tvBalance.setText("- ₹"+calculateBalance(transactionList)*-1);
        }
        else {
            tvBalance.setText("+ ₹"+calculateBalance(transactionList));
        }
    }

    // To load data from shared preference
    private void loadData() {
        SharedPreferences pref = getSharedPreferences("com.cs.ec",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("transactions",null);
        Type type = new TypeToken<ArrayList<TransactionClass>>(){}.getType();
        if(json!=null)
        {
            transactionList=gson.fromJson(json,type);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sendTransaction(double amt, String msg, boolean positive, String c_date) {
        transactionList.add(new TransactionClass(amt,msg,positive,c_date));
        adapter.notifyDataSetChanged();
        rvTransactions.smoothScrollToPosition(transactionList.size()-1);
    }

    // Function to change sign
    private void changeSign() {
        if(positive)
        {
            tvSign.setText("-₹");
            tvSign.setTextColor(Color.parseColor("#F44336"));
            positive = false;
        }
        else {
            tvSign.setText("+₹");
            tvSign.setTextColor(Color.parseColor("#00c853"));
            positive = true;
        }
    }

    // To check if transaction list is empty
    public static void checkIfEmpty(int size) {
        if (size == 0)
        {
            Expense_cal.tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            Expense_cal.tvEmpty.setVisibility(View.GONE);
        }
    }

    // To Calculate Balance by iterating through all transactions
    public static double calculateBalance(ArrayList<TransactionClass> transactionList)
    {
        double bal = 0;
        for(TransactionClass transaction : transactionList)
        {
            if(transaction.isPositive())
            {
                bal+=transaction.getAmount();
            }
            else {
                bal-=transaction.getAmount();
            }
        }
        return bal;
    }

    // Initializing Views
    private void initViews() {
        transactionList = new ArrayList<>();
        tvSign = findViewById(R.id.tvSign);
        rvTransactions = findViewById(R.id.rvTransactions);
        etAmount = findViewById(R.id.etAmount);
        etMessage = findViewById(R.id.etMessage);
        ivSend = findViewById(R.id.ivSend);
        tvEmpty = findViewById(R.id.tvEmpty);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences("com.cs.ec",MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(transactionList);
        editor.putString("transactions",json);
        editor.apply();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
