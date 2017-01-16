package com.feamorx.cookebook.parsers;

import android.util.Log;

import com.feamorx.cookebook.model.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class CategoryJsonSerializer implements Serializer {

    @Override
    public Object save(Object model, Object... args) {
        JSONObject result;
        if (model != null && model instanceof Category) {
            try {
                Category category = (Category) model;
                JSONObject json = new JSONObject();
                json.put("id", category.getId());
                json.put("name", category.getName());
                if (category.getTags() != null && category.getTags().size() > 0) {
                    JSONArray tags = new JSONArray();
                    for (String tag : category.getTags()) {
                        tags.put(tag);
                    }
                    json.put("tags", tags);
                }
                result = json;
            } catch (Exception ex) {
                result = null;
                Log.e(Category.class.getSimpleName(), "Fail to save category", ex);
            }
        } else {
            Log.e(Category.class.getSimpleName(), "Fail to save category to json, model is null or not Category class");
            result = null;
        }
        return result;
    }

    @Override
    public Object load(Object data, Object... args) {
        Category result;
        if (data != null && data instanceof JSONObject) {
            JSONObject json = (JSONObject) data;
            Category category = new Category();
            category.setId(json.optInt("id", -1));
            category.setName(json.optString("name", "<no name Category>"));
            JSONArray tagsJson = json.optJSONArray("tags");
            if (tagsJson != null) {
                ArrayList<String> tags = new ArrayList<>(tagsJson.length());
                for(int i=0; i<tagsJson.length(); i++) {
                    String tag = tagsJson.optString(i);
                    tags.add(tag);
                }
                category.setTags(tags);
            }
            result = category;
        } else {
            Log.e(Category.class.getSimpleName(), "Fail to load category from json, data is null or not JSON class");
            result = null;
        }
        return result;
    }
}
