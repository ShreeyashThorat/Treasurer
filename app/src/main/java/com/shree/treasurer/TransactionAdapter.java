package com.shree.treasurer;


import static com.shree.treasurer.Expense_cal.checkIfEmpty;
import static com.shree.treasurer.Expense_cal.setBalance;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TViewHolder> {
    Context ctx;

    // List containing data for recyclerview
    ArrayList<TransactionClass> transactionList;

    // Constructor for TransactionAdapter
    public TransactionAdapter(Context ctx, ArrayList<TransactionClass> transactionList) {
        this.ctx = ctx;
        this.transactionList = transactionList;
    }

    // On Create View Holder to Inflate transaction row layout
    @NonNull
    @Override
    public TransactionAdapter.TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.transaction_row_layout,parent,false);
        return new TransactionAdapter.TViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TViewHolder holder, int position) {

        // Setting Message to a TextView in Row Layout
        holder.tvMessage.setText(transactionList.get(holder.getAdapterPosition()).getMessage());
        holder.tvDate.setText(transactionList.get(holder.getAdapterPosition()).getDate());
        // If the transaction is Positive (Received Money) set Text Color to Green
        if(transactionList.get(holder.getAdapterPosition()).isPositive())
        {
            holder.tvAmount.setTextColor(Color.parseColor("#00c853"));
            // Setting Amount to a TextView in the row layout
            holder.tvAmount.setText("+₹"+ transactionList.get(holder.getAdapterPosition()).getAmount());
        }

        // If the transaction is Negative (Spent Money) set Text Color to Red
        else {
            holder.tvAmount.setTextColor(Color.parseColor("#F44336"));

            // Setting Amount to a TextView in the row layout
            holder.tvAmount.setText("-₹"+ transactionList.get(holder.getAdapterPosition()).getAmount());
        }

        // On Click Listener for Delete Icon
        holder.ivDelete.setOnClickListener(view -> {
            // Confirmation Alert to delete a Transaction
            @SuppressLint("NotifyDataSetChanged") AlertDialog dialog = new AlertDialog.Builder(ctx)
                    .setCancelable(false)
                    .setTitle("Are you sure? The transaction will be deleted.")
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        transactionList.remove(holder.getAdapterPosition());
                        dialogInterface.dismiss();
                        notifyDataSetChanged();
                        checkIfEmpty(getItemCount());
                        setBalance(transactionList);
                    })
                    .create();
            dialog.show();
        });
    }


    // To get size of the list
    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // View Holder for a Transaction
    public static class TViewHolder extends RecyclerView.ViewHolder{
        TextView tvAmount,tvMessage, tvDate;
        ImageView ivDelete;

        public TViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            tvDate = itemView.findViewById(R.id.tvdate);
        }
    }
}
