package com.example.lucid;

import java.util.Date;
import java.util.List;

public class Dream {
    private String title;
    private String description;
    private String mood;
    private Date date;
    private boolean isLucid;

    public Dream(String title, String description, String mood, Date date, boolean isLucid) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.isLucid = isLucid;
        this.mood = mood;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMood() {
        return mood;
    }

    public Date getDate() {
        return date;
    }

    public boolean isLucid() {
        return isLucid;
    }
}
