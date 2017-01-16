package com.feamorx.cookebook.parsers;

import android.util.Log;

import com.feamorx.cookebook.model.Category;
import com.feamorx.cookebook.model.Ingredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class IngredientJsonSerializer implements Serializer {
    @Override
    public Object save(Object model, Object... args) {
        JSONObject result;
        if (model != null && model instanceof Ingredient) {
            try {
                Ingredient ingredient = (Ingredient) model;
                JSONObject json = new JSONObject();
                json.put("id", ingredient.getId());
                json.put("name", ingredient.getName());
                json.put("description", ingredient.getDescription());

                if (ingredient.getTags() != null && ingredient.getTags().size() > 0) {
                    JSONArray tags = new JSONArray();
                    for (String tag : ingredient.getTags()) {
                        tags.put(tag);
                    }
                    json.put("tags", tags);
                }
                result = json;
            } catch (Exception ex) {
                result = null;
                Log.e(Ingredient.class.getSimpleName(), "Fail to save Ingredient", ex);
            }
        } else {
            Log.e(Ingredient.class.getSimpleName(), "Fail to save Ingredient to json, model is null or not Ingredient class");
            result = null;
        }
        return result;
    }

    @Override
    public Object load(Object data, Object... args) {
        Ingredient result;
        if (data != null && data instanceof JSONObject) {
            JSONObject json = (JSONObject) data;
            Ingredient ingredient = new Ingredient();
            ingredient.setId(json.optInt("id", -1));
            ingredient.setName(json.optString("name", "<no name Ingredient>"));
            ingredient.setDescription(json.optString("description", ""));
            JSONArray tagsJson = json.optJSONArray("tags");
            if (tagsJson != null) {
                ArrayList<String> tags = new ArrayList<>(tagsJson.length());
                for(int i=0; i<tagsJson.length(); i++) {
                    String tag = tagsJson.optString(i);
                    tags.add(tag);
                }
                ingredient.setTags(tags);
            }
            result = ingredient;
        } else {
            Log.e(Ingredient.class.getSimpleName(), "Fail to load Ingredient from json, data is null or not JSON class");
            result = null;
        }
        return result;
    }
}
