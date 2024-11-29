package com.example.lucid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    DreamAdapter dreamAdapter;
    ImageView emptyImageView;
    TextView emptyTextView;

    private void setRecyclerViewDivider() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(1,36);
        drawable.setVisible(false, false);
        itemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        setRecyclerViewDivider();

        addButton = findViewById(R.id.addButton);

        emptyImageView = findViewById(R.id.emptyImageView);
        emptyTextView = findViewById(R.id.emptyTextView);

        if (repository.getDreams().isEmpty()) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = MainActivity.this;
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        dreamAdapter = new DreamAdapter(MainActivity.this, MainActivity.this, repository.getDreams());
        recyclerView.setAdapter(dreamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // TODO Change notifyDataSetChanged to more specific methods
            dreamAdapter.notifyDataSetChanged();
            if (dreamAdapter.getItemCount() != 0) {
                emptyImageView.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.GONE);
            }
        }
        else if (requestCode == 2) {
            dreamAdapter.notifyDataSetChanged();
            if (dreamAdapter.getItemCount() == 0) {
                emptyImageView.setVisibility(View.VISIBLE);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}