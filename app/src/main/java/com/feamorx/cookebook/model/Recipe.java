package com.feamorx.cookebook.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by x86 on 08.01.2017.
 */

public class Recipe extends Model {
    private String name;
    private ArrayList<String> descriptions;
    private String time;
    private Integer minutes;

    private ArrayList<Category> categories;
    private HashMap<String, Category> categoriesByName;

    private ArrayList<RecipeIngredient> ingredients;
    private HashMap<String, RecipeIngredient> ingredientsByName;

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public HashMap<String, RecipeIngredient> getIngredientsByName() {
        return ingredientsByName;
    }

    public void setIngredientsByName(HashMap<String, RecipeIngredient> ingredientsByName) {
        this.ingredientsByName = ingredientsByName;
    }

    public HashMap<String, Category> getCategoriesByName() {
        return categoriesByName;
    }

    public void setCategoriesByName(HashMap<String, Category> categoriesByName) {
        this.categoriesByName = categoriesByName;
    }

    public void addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
            categoriesByName = new HashMap<>();
        }
        categories.add(category);
        categoriesByName.put(category.getName().toUpperCase(), category);
    }


    public void addIngredient(RecipeIngredient recipeIngredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
            ingredientsByName = new HashMap<>();
        }
        ingredients.add(recipeIngredient);
        ingredientsByName.put(recipeIngredient.getIngredient().getName().toUpperCase(), recipeIngredient);
    }
}
