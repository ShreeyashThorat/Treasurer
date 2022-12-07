package com.shree.treasurer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class Investment_route extends AppCompatActivity {
    RecyclerView recyclerView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_route);
        final ActionBar actionbar = getSupportActionBar();
        @SuppressLint("InflateParams") View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.action_bar_text);
        textviewTitle.setText("Fund House List");
        assert actionbar != null;
        actionbar.setCustomView(viewActionBar, params);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        Investor_list[] investorLists = new Investor_list[]{
                new Investor_list("Invesco Mutual Fund", "18002090007", "mfservices@invesco.com", "https://invescomutualfund.com/"),
                new Investor_list("Kotak Mahindra Mutual Fund", "18003091490", "mutual@kotak.com", "https://www.kotakmf.com/"),
                new Investor_list("IDBI Mutual Fund", "1800-419-4324", " contactus@idbimutual.co.in", "https://www.idbimutual.co.in/"),
                new Investor_list("HSBC Mutual Fund", "1800 266 3456", "nriservicesdubai@hsbc.com", "https://www.hsbc.co.in/investments/products/mutual-funds/"),
                new Investor_list("BOI AXA Mutual Fund","18002662676","service@boimf.in","https://www.boimf.in/"),
                new Investor_list("IDFC Mutual Fund","022-66289999","investormf@idfc.com","https://idfcmf.com/"),
                new Investor_list("India Bull MUtual Fund","01246681199","customercare@indiabullsamc.com","https://www.indiabullsamc.com/"),
                new Investor_list("UTI Mutual Fund","1800 266 1230 ","service@uti.co.in","https://www.utimf.com/systematic-investment-plan-2?utm_source=Google_SEM&utm_medium=QI_Brand&utm_term=uti%20mf&utm_campaign=brand_QI_Open&utm_content=RSA&gclid=CjwKCAjwqJSaBhBUEiwAg5W9p5qsFwLpiQtu5rw1yosNqLcdcqiDUSzgoNzONMj0Fw7i1Jy3zOn6PhoChd0QAvD_BwE#utm_campaign_medium=cpc&utm_campaign_source=google_QI_brand_search&utm_adtype=Search&utm_campaign=UTI_MF_Quickinvest_Search_Brand_Exact_Open&utm_adgroup=&utm_ad=&utm_device=c&utm_adposition=&utm_network=g&utm_content=kwd-298550613830&utm_term=uti%20mf"),
                new Investor_list("Edelweiss Mutual Fund","1800-425-0090","EMFHelp@edelweissmf.com","https://www.edelweissmf.com/"),
                new Investor_list("TATA Mutual Fund","02262827777","service@tataamc.com","https://www.tatamutualfund.com/"),
                new Investor_list("IIFL Mutual Fund","1800 419 2267","service@iiflw.com","https://www.iiflmf.com/"),

        };
        recyclerView = findViewById(R.id.recyclerView);
        Investor_adapter adapter = new Investor_adapter(investorLists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}