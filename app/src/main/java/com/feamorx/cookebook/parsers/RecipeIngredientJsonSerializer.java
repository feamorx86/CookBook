package com.feamorx.cookebook.parsers;

import android.text.TextUtils;
import android.util.Log;

import com.feamorx.cookebook.model.Category;
import com.feamorx.cookebook.model.IngredientStorage;
import com.feamorx.cookebook.model.RecipeIngredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class RecipeIngredientJsonSerializer implements Serializer {

    @Override
    public Object save(Object model, Object... args) {
        JSONObject result;
        if (model != null && model instanceof RecipeIngredient) {
            try {
                RecipeIngredient recipeIngredient = (RecipeIngredient) model;
                JSONObject json = new JSONObject();
                json.put("id", recipeIngredient.getId());
                if (recipeIngredient.getIngredient() != null) {
                    json.put("ingredientId", recipeIngredient.getIngredient().getId());
                }
                if (recipeIngredient.getCount() != null) {
                    json.put("count", recipeIngredient.getCount().doubleValue());
                }
                if (!TextUtils.isEmpty(recipeIngredient.getStrCount())) {
                    json.put("strCount", recipeIngredient.getStrCount());
                }
                result = json;
            } catch (Exception ex) {
                result = null;
                Log.e(RecipeIngredient.class.getSimpleName(), "Fail to save RecipeIngredient", ex);
            }
        } else {
            Log.e(RecipeIngredient.class.getSimpleName(), "Fail to save RecipeIngredient to json, model is null or not RecipeIngredient class");
            result = null;
        }
        return result;
    }

    @Override
    public Object load(Object data, Object... args) {
        RecipeIngredient result;
        IngredientStorage ingredientStorage = (IngredientStorage) args[0];
        if (data != null && data instanceof JSONObject) {
            JSONObject json = (JSONObject) data;
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setId(json.optInt("id", -1));
            if (json.has("count")) {
                recipeIngredient.setCount(json.optDouble("count"));
            } else {
                recipeIngredient.setCount(null);
            }
            recipeIngredient.setStrCount(json.optString("strCount", ""));
            int ingredientId = json.optInt("ingredientId", -1);
            if (ingredientId != -1) {
                recipeIngredient.setIngredient(ingredientStorage.byId(ingredientId));
            }

            result = recipeIngredient;
        } else {
            Log.e(RecipeIngredient.class.getSimpleName(), "Fail to load RecipeIngredient from json, data is null or not JSON class");
            result = null;
        }
        return result;
    }
}
