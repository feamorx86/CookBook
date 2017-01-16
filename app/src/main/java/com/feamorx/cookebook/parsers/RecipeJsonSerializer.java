package com.feamorx.cookebook.parsers;

import android.text.TextUtils;
import android.util.Log;

import com.feamorx.cookebook.model.Category;
import com.feamorx.cookebook.model.CategoryStorage;
import com.feamorx.cookebook.model.IngredientStorage;
import com.feamorx.cookebook.model.Recipe;
import com.feamorx.cookebook.model.RecipeIngredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class RecipeJsonSerializer implements Serializer {

    @Override
    public Object save(Object model, Object... args) {
        JSONObject result;
        if (model != null && model instanceof Recipe) {
            try {
                Recipe recipe = (Recipe) model;
                JSONObject json = new JSONObject();
                json.put("id", recipe.getId());
                if (!TextUtils.isEmpty(recipe.getName())) {
                    json.put("name", recipe.getName());
                }
                if (!TextUtils.isEmpty(recipe.getTime())) {
                    json.put("time", recipe.getTime());
                }
                if (recipe.getMinutes() != null) {
                    json.put("minutes", recipe.getMinutes().intValue());
                }
                if (recipe.getTags() != null && recipe.getTags().size() > 0) {
                    JSONArray tags = new JSONArray();
                    for (String tag : recipe.getTags()) {
                        tags.put(tag);
                    }
                    json.put("tags", tags);
                }

                if (recipe.getCategories() != null && recipe.getCategories().size() > 0) {
                    JSONArray categories = new JSONArray();
                    for (Category category : recipe.getCategories()) {
                        categories.put(category.getId());
                    }
                    json.put("categories", categories);
                }

                if (recipe.getDescriptions() != null && recipe.getDescriptions().size() > 0) {
                    JSONArray descriptions = new JSONArray();
                    for (String description : recipe.getDescriptions()) {
                        descriptions.put(description);
                    }
                    json.put("descriptions", descriptions);
                }

                if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
                    JSONArray ingredients = new JSONArray();
                    Serializer recipeIngredientSerializer = ParserFactory.get().forClass(RecipeIngredientJsonSerializer.class);
                    for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
                        JSONObject recipeIngredientJson = (JSONObject) recipeIngredientSerializer.save(recipeIngredient);
                        ingredients.put(recipeIngredientJson);
                    }
                    json.put("ingredients", ingredients);
                }

                result = json;
            } catch (Exception ex) {
                result = null;
                Log.e(Recipe.class.getSimpleName(), "Fail to save Recipe", ex);
            }
        } else {
            Log.e(Recipe.class.getSimpleName(), "Fail to save Recipe to json, model is null or not Recipe class");
            result = null;
        }
        return result;
    }

    @Override
    public Object load(Object data, Object... args) {
        Recipe result;
        IngredientStorage ingredientStorage = (IngredientStorage) args[0];
        CategoryStorage categoryStorage = (CategoryStorage) args[1];
        if (data != null && data instanceof JSONObject) {
            JSONObject json = (JSONObject) data;
            Recipe recipe = new Recipe();
            recipe.setId(json.optInt("id", -1));
            recipe.setName(json.optString("name", ""));
            recipe.setTime(json.optString("time"));
            if (json.has("minutes")) {
                recipe.setMinutes(json.optInt("minutes"));
            } else {
                recipe.setMinutes(null);
            }

            JSONArray tagsJson = json.optJSONArray("tags");
            if (tagsJson != null) {
                ArrayList<String> tags = new ArrayList<>(tagsJson.length());
                for(int i=0; i<tagsJson.length(); i++) {
                    String tag = tagsJson.optString(i);
                    tags.add(tag);
                }
                recipe.setTags(tags);
            }

            JSONArray categoriesJson = json.optJSONArray("categories");
            if (categoriesJson != null) {
                for(int i=0; i<categoriesJson.length(); i++) {
                    int categoryId = categoriesJson.optInt(i, -1);
                    Category category = categoryStorage.byId(categoryId);
                    if (category != null) {
                        recipe.addCategory(category);
                    }
                }
            }

            JSONArray descriptionsJson = json.optJSONArray("descriptions");
            if (descriptionsJson != null) {
                ArrayList<String> descriptions = new ArrayList<>(descriptionsJson.length());
                for(int i=0; i<descriptionsJson.length(); i++) {
                    String tag = descriptionsJson.optString(i);
                    descriptions.add(tag);
                }
                recipe.setDescriptions(descriptions);
            }

            JSONArray ingredientsJson = json.optJSONArray("ingredients");
            if (ingredientsJson != null) {
                Serializer recipeIngredientSerializer = ParserFactory.get().forClass(RecipeIngredientJsonSerializer.class);
                for(int i=0; i<ingredientsJson.length(); i++) {
                    JSONObject recipeIngredientJson = ingredientsJson.optJSONObject(i);
                    RecipeIngredient recipeIngredient = (RecipeIngredient) recipeIngredientSerializer.load(recipeIngredientJson, ingredientStorage);
                    if (recipeIngredient!= null) {
                        recipe.addIngredient(recipeIngredient);
                    }
                }
            }

            result = recipe;
        } else {
            Log.e(Recipe.class.getSimpleName(), "Fail to load Recipe from json, data is null or not JSON class");
            result = null;
        }
        return result;
    }
}
