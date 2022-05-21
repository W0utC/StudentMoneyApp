package com.example.studentmoneyapp.model;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.utils.TransactionClass;
import com.example.studentmoneyapp.utils.TransactionRecView;

import java.util.ArrayList;

public class TransactionsRecViewAdapter extends RecyclerView.Adapter<TransactionsRecViewAdapter.ViewHolder>{

    private ArrayList<TransactionRecView> transactions = new ArrayList<>();

    private Context context;

    public TransactionsRecViewAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String date = transactions.get(position).getDate().toString().substring(0, 10); //original: String date = allTransactions.getSingleTransactionList().get(pos).getDate().toString();
        String amount = String.valueOf(transactions.get(position).getAmount()); //original: String amount = String.valueOf(allTransactions.getSingleTransactionList().get(pos).getAmount());
        String store;
        if(transactions.get(position).getStore().equals("-1")) store = TransactionClass.getInstance().getList().get(position).getType();
        else store = transactions.get(position).getStore(); //original: String store = allTransactions.getSingleTransactionList().get(pos).getStore();
        String euro = "\u20ac";
        String tempAmount  = euro + " " + amount;

        setColor(holder, position);

        holder.txtDate.setText(date);
        holder.txtAmount.setText(tempAmount);
        holder.txtStore.setText(store);
        //holder.transactionListItemParent.setElevation(0);
        holder.transactionListItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, transactions.get(holder.getAdapterPosition()).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setColor(ViewHolder holder, int pos){

        if(transactions.get(pos).getAmount() > 0) {
            int expenseGreen = ContextCompat.getColor(context,R.color.expenseGreen);
            holder.transactionListItemParent.setCardBackgroundColor(expenseGreen);
        }
        else if(transactions.get(pos).getAmount() <= 0) {
            int expenseRed = ContextCompat.getColor(context,R.color.expenseRed);
            holder.transactionListItemParent.setCardBackgroundColor(expenseRed);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(ArrayList<TransactionRecView> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtDate, txtAmount, txtStore;
        private CardView transactionListItemParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtTransactionListDate);
            txtAmount = itemView.findViewById(R.id.txtTransactionListAmount);
            txtStore = itemView.findViewById(R.id.txtTransactionListStore);
            transactionListItemParent = itemView.findViewById(R.id.transaction_list_item_parent);
        }
    }
}
