package com.feamorx.cookebook.model;

import com.feamorx.cookebook.parsers.DataJsonSerializer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by x86 on 11.01.2017.
 */
public class MasterData {

    public CategoryStorage categoryStorage;
    public IngredientStorage ingredientStorage;
    public ArrayList<Recipe> recipes;
    public Date updateTime;
    public Integer version;

    public boolean needUpdateByVersion(MasterData master) {
        boolean result;
        if (version == null || master.version == null) {
            result = true;
        } else if (version >= master.version) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public Recipe getRecipe(int recipeId) {
        Recipe result = null;
        if (recipes != null && recipes.size() > 0) {
            for (Recipe recipe : recipes) {
                if (recipe.getId() == recipeId) {
                    result = recipe;
                    break;
                }
            }
        }
        return result;
    }
}
