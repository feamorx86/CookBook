package com.feamorx.cookebook.model;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by x86 on 08.01.2017.
 */

public class CategoryStorage {
    private ArrayList<Category> categories;
    private HashMap<Integer, Category> categoriesById;
    private HashMap<String, Category> categoriesByName;

    public CategoryStorage() {
        categories = new ArrayList<>();
        categoriesById = new HashMap<>();
        categoriesByName = new HashMap<>();
    }

    public void clear() {
        categories.clear();
        categoriesById.clear();
        categoriesByName.clear();
    }

    public Category byId(int id) {
        Category result = categoriesById.get(id);
        return result;
    }

    public Category byName(String name) {
        Category result = null;
        if (!TextUtils.isEmpty(name)) {
            String upperName = name.toUpperCase();
            result = categoriesByName.get(name);
        }
        return result;
    }

    public ArrayList<Category> byTags(String ... tags) {
        ArrayList<Category> result = null;
        if (tags!=null && tags.length > 0 && categories.size() > 0) {
            result = new ArrayList<>();
            for(Category category : categories) {
                if (category.hasTags(tags)) {
                    result.add(category);
                }
            }
        }
        return result;
    }

    public void addCategory(Category category) {
        categories.add(category);
        categoriesByName.put(category.getName(), category);
        categoriesById.put(category.getId(), category);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public HashMap<Integer, Category> getCategoriesById() {
        return categoriesById;
    }

    public void setCategoriesById(HashMap<Integer, Category> categoriesById) {
        this.categoriesById = categoriesById;
    }

    public HashMap<String, Category> getCategoriesByName() {
        return categoriesByName;
    }

    public void setCategoriesByName(HashMap<String, Category> categoriesByName) {
        this.categoriesByName = categoriesByName;
    }
}
