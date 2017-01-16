package com.feamorx.cookebook.utils;

import com.feamorx.cookebook.model.MasterData;

import java.util.ArrayList;

/**
 * Created by x86 on 09.01.2017.
 */

public class DataManager {
    private static DataManager instance;

    private MasterData masterData;
    private ArrayList<Integer> nowCookRecipes;

    private DataManager() {

    }

    public static DataManager get() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public synchronized MasterData getMasterData() {
        return masterData;
    }

    public synchronized void setMasterData(MasterData masterData) {
        this.masterData = masterData;
    }

    public ArrayList<Integer> getNowCookRecipes() {
        return nowCookRecipes;
    }

    public void setNowCookRecipes(ArrayList<Integer> nowCookRecipes) {
        this.nowCookRecipes = nowCookRecipes;
    }
}
