package net.example.data.model;

import net.example.data.validation.ValidRegex;

public class Group {
    private int id;
    private String name;

    @ValidRegex(regex = ".{4,}", message = "valid-name-length")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
