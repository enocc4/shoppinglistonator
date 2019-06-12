package com.blissfulhazulnut.shoppinglistonator.models;

public class RecipeNotFoundException extends Throwable {
    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }

}
