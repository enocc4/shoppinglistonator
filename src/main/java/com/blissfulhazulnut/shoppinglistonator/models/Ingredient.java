package com.blissfulhazulnut.shoppinglistonator.models;

public class Ingredient {
    Integer ingred_id;
    String ingred_name;

    /** Timestamp of first upload */
    java.util.Date created;

    public String toString()
    {
        return "Ingredient " + ingred_id + ":" + ingred_name;
    }

    public Ingredient() {
    }

    public Integer getId() {
        return ingred_id;
    }

    public void setId(Integer id) {
        this.ingred_id = id;
    }

    public String getName() {
        return ingred_name;
    }

    public void setName(String title) {
        this.ingred_name = title;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }
}

