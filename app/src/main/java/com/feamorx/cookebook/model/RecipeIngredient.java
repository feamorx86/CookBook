package com.feamorx.cookebook.model;

/**
 * Created by x86 on 08.01.2017.
 */

public class RecipeIngredient {
    private int id;
    private String strCount;
    private Double count;
    private Ingredient ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrCount() {
        return strCount;
    }

    public void setStrCount(String strCount) {
        this.strCount = strCount;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
