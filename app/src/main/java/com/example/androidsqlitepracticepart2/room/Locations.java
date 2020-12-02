package com.example.androidsqlitepracticepart2.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;
@Entity(tableName = "locations")
public class Locations {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Locations() {
    }


    public Locations(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
