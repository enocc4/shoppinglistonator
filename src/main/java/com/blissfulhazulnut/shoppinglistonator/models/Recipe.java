package com.blissfulhazulnut.shoppinglistonator.models;

public class Recipe {

    Integer id;
    String name;
    String description;
    String instructions;



    /** Timestamp of first upload */
    //java.util.Date created;

    public String toString()
    {
        return "Recipe " + id + ":" + name + ":" + description + ":" + instructions;

    }

    public Recipe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
