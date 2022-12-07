package com.shree.treasurer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Investor_adapter extends RecyclerView.Adapter<Investor_adapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final Investor_list[] investorLists;
    private static final int REQUEST_CALL = 1;
    private Context context;

    public Investor_adapter(Investor_list[] investorLists) {
        this.investorLists = investorLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.investment_route_dsn, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.investorName.setText(investorLists[position].getInvestor_name());
        holder.investorCall.setOnClickListener(view -> {
            String contact = investorLists[position].getInvestor_contact();
            if (ContextCompat.checkSelfPermission(view.getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) view.getContext(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + contact));
                view.getContext().startActivity(phone_intent);
            }
        });
        holder.investorEmail.setOnClickListener(view -> {
            String email_= investorLists[position].getInvestor_email();
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email_});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            view.getContext().startActivity(intent);
        });
        holder.investorWebsite.setOnClickListener(view -> {
            String url = investorLists[position].getInvestor_website();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            view.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return investorLists.length;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{
                Toast.makeText(context.getApplicationContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView investorName;
        public Button investorCall, investorEmail, investorWebsite;
        public ViewHolder(View itemView) {
            super(itemView);
            this.investorName = itemView.findViewById(R.id.investor_name);
            this.investorCall = itemView.findViewById(R.id.investor_call);
            this.investorEmail = itemView.findViewById(R.id.investor_email);
            this.investorWebsite = itemView.findViewById(R.id.investor);
        }
    }
}
