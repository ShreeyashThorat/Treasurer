package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button sign_up, sign_in;
    ImageView insta;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.RoyalBlue));
        }
        sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);
        });
        sign_in = findViewById(R.id.sign_in);
        sign_in.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        });
        insta = findViewById(R.id.insta);
        insta.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Edu_calculator.class);
            startActivity(i);
        });
    }
}