package com.blissfulhazulnut.shoppinglistonator.models;

public class Ingredient {
    Integer id;
    String name;


    /** Timestamp of first upload */
    //java.util.Date created;

    public String toString()
    {
        return "Ingredient " + id + ":" + name;
    }

    public Ingredient() {
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


    //public void setCreated(java.util.Date created) {
      //  this.created = created;
    //}
}

