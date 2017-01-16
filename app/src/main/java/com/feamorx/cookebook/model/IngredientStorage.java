package com.feamorx.cookebook.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by x86 on 08.01.2017.
 */

public class IngredientStorage {
    private ArrayList<Ingredient> ingredients;
    private HashMap<Integer, Ingredient> ingredientsById;
    private HashMap<String, Ingredient> ingredientsByName;

    public IngredientStorage() {
        ingredients = new ArrayList<>();
        ingredientsById = new HashMap<>();
        ingredientsByName = new HashMap<>();
    }

    public void clear() {
        ingredients.clear();
        ingredientsById.clear();
        ingredientsByName.clear();
    }

    public Ingredient byId(int id) {
        Ingredient result = ingredientsById.get(id);
        return result;
    }

    public Ingredient byName(String name) {
        Ingredient result = null;
        if (!TextUtils.isEmpty(name)) {
            String upperName = name.toUpperCase();
            result = ingredientsByName.get(name);
        }
        return result;
    }

    public ArrayList<Ingredient> byTags(String ... tags) {
        ArrayList<Ingredient> result = null;
        if (tags!=null && tags.length > 0 && ingredients.size() > 0) {
            result = new ArrayList<>();
            for(Ingredient ingredient : ingredients) {
                if (ingredient.hasTags(tags)) {
                    result.add(ingredient);
                }
            }
        }
        return result;
    }

    public void addIngredient(Ingredient category) {
        ingredients.add(category);
        ingredientsByName.put(category.getName(), category);
        ingredientsById.put(category.getId(), category);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public HashMap<Integer, Ingredient> getIngredientsById() {
        return ingredientsById;
    }

    public void setIngredientsById(HashMap<Integer, Ingredient> ingredientsById) {
        this.ingredientsById = ingredientsById;
    }

    public HashMap<String, Ingredient> getIngredientsByName() {
        return ingredientsByName;
    }

    public void setIngredientsByName(HashMap<String, Ingredient> ingredientsByName) {
        this.ingredientsByName = ingredientsByName;
    }
}
