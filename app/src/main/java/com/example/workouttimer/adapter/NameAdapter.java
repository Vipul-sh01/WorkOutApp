package com.example.workouttimer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.R;
import com.example.workouttimer.data.local.models.NameModel;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder> {

    private List<NameModel> nameList = new ArrayList<>();

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_name, parent, false);
        return new NameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        NameModel current = nameList.get(position);
        holder.textViewName.setText(current.getName());
        holder.textViewEmail.setText(current.getEmail());
        holder.textViewAge.setText(String.valueOf(current.getAge()));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public void setNames(List<NameModel> names) {
        this.nameList = names;
        notifyDataSetChanged();
    }

    static class NameViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewEmail;
        private TextView textViewAge;

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAge = itemView.findViewById(R.id.textViewAge);
        }
    }
}
