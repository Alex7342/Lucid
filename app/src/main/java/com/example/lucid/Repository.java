package com.example.lucid;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static List<Dream> dreamList = new ArrayList<>();
    private final Context context;

    public Repository(@Nullable Context context) {
        this.context = context;
    }

    public List<Dream> getDreams() {
        return dreamList;
    }

    public void addDream(String title, String description) {
        try {
            dreamList.add(new Dream(title, description));
            Toast.makeText(context, "Dream added successfully!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception exception) {
            Toast.makeText(context, "Failed to add the dream!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDream(int id, String newTitle, String newDescription) {
        try {
            dreamList.set(id, new Dream(newTitle, newDescription));
            Toast.makeText(context, "Dream updated successfully!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception exception) {
            Toast.makeText(context, "Could not update the dream!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDream(int id) {
        try {
            dreamList.remove(id);
            Toast.makeText(context, "Dream deleted successfully!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception exception) {
            Toast.makeText(context, "Failed to delete the dream!", Toast.LENGTH_SHORT).show();
        }
    }
}
