package net.example.data.model;

import net.example.data.validation.NotEmpty;
import net.example.data.validation.ValidNumber;
import net.example.data.validation.ValidRegex;

public class User {
    private int id;
    private String name;
    private int age;
    private Group group;

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @ValidRegex(regex = ".{4,}", message = "valid-name-length")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ValidNumber(min = 5, max = 90, message = "valid-age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotEmpty("valid-group")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
