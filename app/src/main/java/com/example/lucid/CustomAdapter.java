package com.example.lucid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    private List<Dream> dreams;

    public CustomAdapter(Activity activity, Context context, List<Dream> dreams) {
        this.activity = activity;
        this.context = context;
        this.dreams = dreams;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dreamIdTextView.setText(String.valueOf(position + 1));
        holder.dreamTitleTextView.setText(String.valueOf(dreams.get(position).getTitle()));
        holder.dreamDescriptionTextView.setText(String.valueOf(dreams.get(position).getDescription()));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(position));
                intent.putExtra("title", String.valueOf(dreams.get(position).getTitle()));
                intent.putExtra("description", String.valueOf(dreams.get(position).getDescription()));
                intent.putExtra("mood", String.valueOf(dreams.get(position).getMood()));
                intent.putExtra("date", dreams.get(position).getDate());
                intent.putExtra("isLucid", dreams.get(position).isLucid());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dreamIdTextView, dreamTitleTextView, dreamDescriptionTextView;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dreamIdTextView = itemView.findViewById(R.id.dreamIdTextView);
            dreamTitleTextView = itemView.findViewById(R.id.dreamTitleTextView);
            dreamDescriptionTextView = itemView.findViewById(R.id.dreamDescriptionTextView);
            rowLayout = itemView.findViewById(R.id.recyclerViewRowLayout);
        }
    }

}
