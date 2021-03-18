package com.example.shanksvilla.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> names;
    private List<Boolean> isAvailable;

    public CardAdapter(LayoutInflater layoutInflater, List<String> names, List<Boolean> isAvailable) {
        this.layoutInflater = layoutInflater;
        this.names = names;
        this.isAvailable= isAvailable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name= names.get(position);
        holder.name.setText(name);
        if (isAvailable.get(position)){
            holder.aOn.setText("Available");
            holder.aOn.setTextColor(R.color.red);
        }else{
            holder.aOn.setTextColor(R.color.green);
        }

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, aOn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name_card);
            aOn= itemView.findViewById(R.id.text_card);
        }
    }
}
