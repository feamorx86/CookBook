package com.feamorx.cookebook.model;

import java.util.ArrayList;

/**
 * Created by x86 on 08.01.2017.
 */

public class Model {
    protected int id;
    protected ArrayList<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean hasTags(String ... tags) {
        for (String modelTag : this.getTags()) {
            for(String tag : tags) {
                if (modelTag.equalsIgnoreCase(tag))
                    return true;
            }
        }
        return false;
    }
}
