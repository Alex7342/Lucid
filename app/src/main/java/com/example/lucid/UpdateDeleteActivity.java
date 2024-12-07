package com.example.lucid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.lucid.database.local.DreamDatabaseHelper;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Date;

public class UpdateDeleteActivity extends AppCompatActivity {
    EditText titleInput, descriptionInput, moodInput;
    MaterialSwitch isLucidInput;
    Button updateButton, deleteButton;
    int position, id;
    String title, description, mood;
    Date date;
    boolean isLucid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        titleInput = findViewById(R.id.titleUpdateInput);
        descriptionInput = findViewById(R.id.descriptionUpdateInput);
        moodInput = findViewById(R.id.moodUpdateInput);
        isLucidInput = findViewById(R.id.isLucidUpdateInput);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        getAndSetIntentData();

        // Set the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        isLucidInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Change the background of the activity based on isChecked (if the dream is lucid or not)
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleInput.getText().toString().trim();
                description = descriptionInput.getText().toString().trim();
                mood = moodInput.getText().toString().trim();
                isLucid = isLucidInput.isChecked();

                DreamDatabaseHelper databaseHelper = new DreamDatabaseHelper(UpdateDeleteActivity.this);
                databaseHelper.updateDream(id, title, description, mood, isLucid);
                databaseHelper.close();

                Intent data = new Intent();
                data.putExtra("indexUpdated", position); // Index of the updated element
                setResult(RESULT_OK, data);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    private void getAndSetIntentData() {
        // Getting data from intent
        if (getIntent().hasExtra("position")) {
            position = getIntent().getIntExtra("position", 0);
        }
        else {
            Toast.makeText(this, "Unable to find the dream (no valid position in the list)!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", 0);
        }
        else {
            Toast.makeText(this, "Unable to find the dream (no valid id)!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }

        if (getIntent().hasExtra("description")) {
            description = getIntent().getStringExtra("description");
        }

        if (getIntent().hasExtra("date")) {
            date = (Date) getIntent().getSerializableExtra("date");
        }

        if (getIntent().hasExtra("mood")) {
            mood = getIntent().getStringExtra("mood");
        }

        if (getIntent().hasExtra("isLucid")) {
            isLucid = getIntent().getBooleanExtra("isLucid", false);
        }

        // Setting intent data
        titleInput.setText(title);
        descriptionInput.setText(description);
        moodInput.setText(mood);
        isLucidInput.setChecked(isLucid);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + "\"" + title + "\"" + "?");
        builder.setMessage("Are you sure you want to delete the dream " + "\"" + title + "\"" + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DreamDatabaseHelper databaseHelper = new DreamDatabaseHelper(UpdateDeleteActivity.this);
                databaseHelper.deleteDream(id);
                databaseHelper.close();

                Intent data = new Intent();
                data.putExtra("indexDeleted", position); // index of the deleted element
                setResult(RESULT_OK, data);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
}