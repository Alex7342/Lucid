package com.example.lucid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucid.database.local.DreamDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DreamAdapter extends RecyclerView.Adapter<DreamAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    private List<Dream> dreams;

    public DreamAdapter(Activity activity, Context context, List<Dream> dreams) {
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
        setDreamContent(holder, position);

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdateDeleteActivity(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView moodCardView;
        TextView monthTextView, dayTextView, dreamTitleTextView, dreamDescriptionTextView, dreamMoodTextView;
        ImageView dreamIsLucidImageView;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            monthTextView = itemView.findViewById(R.id.monthTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            dreamTitleTextView = itemView.findViewById(R.id.dreamTitleTextView);
            dreamDescriptionTextView = itemView.findViewById(R.id.dreamDescriptionTextView);
            dreamMoodTextView = itemView.findViewById(R.id.moodTextView);
            moodCardView = itemView.findViewById(R.id.moodCardView);
            dreamIsLucidImageView = itemView.findViewById(R.id.isLucidImageView);
            rowLayout = itemView.findViewById(R.id.recyclerViewRowLayout);
        }
    }

    private void setDreamContent(@NonNull MyViewHolder holder, int position) {
        // Set the content for the month and day TextViews
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM", Locale.US);
        holder.monthTextView.setText(simpleDateFormat.format(dreams.get(position).getDate()));
        simpleDateFormat = new SimpleDateFormat("d", Locale.US);
        holder.dayTextView.setText(simpleDateFormat.format(dreams.get(position).getDate()));

        // Set the content for the title and description TextViews
        holder.dreamTitleTextView.setText(String.valueOf(dreams.get(position).getTitle()));
        holder.dreamDescriptionTextView.setText(String.valueOf(dreams.get(position).getDescription()));

        // Set the content for the mood custom tag or delete it if there is no content
        if (dreams.get(position).getMood().isEmpty()) {
            holder.moodCardView.setVisibility(View.GONE);
        }
        else {
            holder.dreamMoodTextView.setText(String.valueOf(dreams.get(position).getMood()));
        }

        // Set the content for the ImageView that describes the state of isLucid
        if (dreams.get(position).isLucid()) {
            holder.dreamIsLucidImageView.setImageResource(R.drawable.lucid);
            holder.dreamIsLucidImageView.setVisibility(View.VISIBLE);
        }
        else {
            //holder.dreamIsLucidImageView.setImageResource(R.drawable.not_lucid);
            holder.dreamIsLucidImageView.setVisibility(View.GONE);
        }
    }

    private void startUpdateDeleteActivity(int position) {
        Intent intent = new Intent(context, UpdateDeleteActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", dreams.get(position).getId());
        intent.putExtra("title", String.valueOf(dreams.get(position).getTitle()));
        intent.putExtra("description", String.valueOf(dreams.get(position).getDescription()));
        intent.putExtra("mood", String.valueOf(dreams.get(position).getMood()));
        intent.putExtra("date", dreams.get(position).getDate());
        intent.putExtra("isLucid", dreams.get(position).isLucid());
        activity.startActivityForResult(intent, 2);
    }

    public void fetchDataFromDatabase() {
        DreamDatabaseHelper databaseHelper = new DreamDatabaseHelper(context);
        dreams = databaseHelper.getDreams();
        databaseHelper.close();
    }
}
