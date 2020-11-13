package com.example.androidsqlitepracticepart2;

import java.util.UUID;

public class Location {
    private UUID id;
    private String name;

    public Location() {
    }


    public Location( String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
