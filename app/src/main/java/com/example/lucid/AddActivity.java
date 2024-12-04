package com.example.lucid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Date;

public class AddActivity extends AppCompatActivity {
    EditText titleInput;
    EditText descriptionInput;
    EditText moodInput;
    MaterialSwitch isLucidInput;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        moodInput = findViewById(R.id.moodInput);
        isLucidInput = findViewById(R.id.isLucidInput);
        addButton = findViewById(R.id.addButton);

        isLucidInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Change the background of the activity based on isChecked (if the dream is lucid or not)
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository repository = new Repository(AddActivity.this);

                String title = titleInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();
                String mood = moodInput.getText().toString().trim();
                boolean isLucid = isLucidInput.isChecked();

                repository.addDream(title, description, mood, new Date(), isLucid);

                Intent data = new Intent();
                data.putExtra("indexAdded", repository.getDreams().size() - 1); // Index of the added element
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}