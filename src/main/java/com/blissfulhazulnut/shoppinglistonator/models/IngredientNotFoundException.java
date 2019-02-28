package com.blissfulhazulnut.shoppinglistonator.models;

public class IngredientNotFoundException extends Throwable {
    public IngredientNotFoundException() {
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }

}
