package com.feamorx.cookebook.model;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class Ingredient extends Model {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
