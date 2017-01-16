package com.feamorx.cookebook.parsers;

import android.text.TextUtils;
import android.util.Log;

import com.feamorx.cookebook.model.Category;
import com.feamorx.cookebook.model.CategoryStorage;
import com.feamorx.cookebook.model.Ingredient;
import com.feamorx.cookebook.model.IngredientStorage;
import com.feamorx.cookebook.model.MasterData;
import com.feamorx.cookebook.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class DataJsonSerializer implements Serializer {

    public static final SimpleDateFormat UPDATE_DATE_FORMAT = new SimpleDateFormat("DD-MM-yyyy HH:mm:ss");

    @Override
    public Object save(Object model, Object... args) {
        MasterData data = (MasterData) model;
        JSONObject json;
        try {
            json = new JSONObject();
            if (data.categoryStorage != null && data.categoryStorage.getCategories().size() > 0) {
                JSONArray categoriesJson = new JSONArray();
                CategoryJsonSerializer serializer = (CategoryJsonSerializer) ParserFactory.get().forClass(CategoryJsonSerializer.class);
                for (Category category : data.categoryStorage.getCategories()) {
                    JSONObject categoryJson = (JSONObject) serializer.save(category);
                    categoriesJson.put(categoryJson);
                }
                json.put("categories", categoriesJson);
            }

            if (data.ingredientStorage != null && data.ingredientStorage.getIngredients().size() > 0) {
                JSONArray ingredientsJson = new JSONArray();
                IngredientJsonSerializer serializer = (IngredientJsonSerializer) ParserFactory.get().forClass(IngredientJsonSerializer.class);
                for (Ingredient ingredient : data.ingredientStorage.getIngredients()) {
                    JSONObject ingredientJson = (JSONObject) serializer.save(ingredient);
                    ingredientsJson.put(ingredientJson);
                }
                json.put("ingredients", ingredientsJson);
            }

            if (data.recipes != null && data.recipes.size() > 0) {
                JSONArray recipesJson = new JSONArray();
                RecipeJsonSerializer serializer = (RecipeJsonSerializer) ParserFactory.get().forClass(RecipeJsonSerializer.class);
                for (Recipe recipe : data.recipes) {
                    JSONObject recipeJson = (JSONObject) serializer.save(recipe);
                    recipesJson.put(recipeJson);
                }
                json.put("recipes", recipesJson);
            }

            if (data.updateTime != null) {
                json.put("updateTime", UPDATE_DATE_FORMAT.format(data.updateTime));
            }

            if (data.version != null) {
                json.put("version", data.version.intValue());
            }
        }catch (JSONException jsEx) {
            Log.e(DataJsonSerializer.class.getSimpleName(), "Fail to save", jsEx);
            json = null;
        }

        return json;
    }

    @Override
    public Object load(Object data, Object... args) {

        MasterData dataStorage = new MasterData();
        JSONObject json = (JSONObject) data;

        JSONArray categoriesJson = json.optJSONArray("categories");
        if (categoriesJson != null) {
            dataStorage.categoryStorage = new CategoryStorage();
            CategoryJsonSerializer serializer = (CategoryJsonSerializer) ParserFactory.get().forClass(CategoryJsonSerializer.class);
            for (int i=0; i<categoriesJson.length(); i++) {
                JSONObject categoryJson = categoriesJson.optJSONObject(i);
                Category category = (Category) serializer.load(categoryJson);
                dataStorage.categoryStorage.addCategory(category);
            }
        }

        JSONArray ingredientsJson = json.optJSONArray("ingredients");
        if (ingredientsJson  != null) {
            dataStorage.ingredientStorage = new IngredientStorage();
            IngredientJsonSerializer serializer = (IngredientJsonSerializer) ParserFactory.get().forClass(IngredientJsonSerializer.class);
            for (int i=0; i<ingredientsJson.length(); i++) {
                JSONObject ingredientJson = ingredientsJson.optJSONObject(i);
                Ingredient ingredient = (Ingredient) serializer.load(ingredientJson);
                dataStorage.ingredientStorage.addIngredient(ingredient);
            }
        }

        JSONArray recipesJson = json.optJSONArray("recipes");
        if (recipesJson  != null) {
            dataStorage.recipes = new ArrayList<>();
            RecipeJsonSerializer serializer = (RecipeJsonSerializer) ParserFactory.get().forClass(RecipeJsonSerializer.class);
            for (int i=0; i<recipesJson.length(); i++) {
                JSONObject recipeJson = recipesJson.optJSONObject(i);
                Recipe recipe = (Recipe) serializer.load(recipeJson, dataStorage.ingredientStorage, dataStorage.categoryStorage);
                dataStorage.recipes.add(recipe);
            }
        }
        String updateTime = json.optString("updateTime");
        if (!TextUtils.isEmpty(updateTime)) {
            try {
                dataStorage.updateTime = UPDATE_DATE_FORMAT.parse(updateTime);
            } catch (ParseException e) {
                dataStorage.updateTime = null;
                Log.e(DataJsonSerializer.class.getSimpleName(), "Fail to parse update time : "+updateTime, e);
            }
        } else {
            dataStorage.updateTime = null;
        }

        if (json.has("version")) {
            dataStorage.version = json.optInt("version");
        }
        return dataStorage;
    }
}
