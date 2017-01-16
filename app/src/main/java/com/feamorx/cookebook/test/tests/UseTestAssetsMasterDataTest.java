package com.feamorx.cookebook.test.tests;

import android.content.Context;

import com.feamorx.cookebook.model.MasterData;
import com.feamorx.cookebook.test.Points;
import com.feamorx.cookebook.test.Test;
import com.feamorx.cookebook.utils.DataManager;
import com.feamorx.cookebook.utils.DataStorage;

import java.util.ArrayList;

/**
 * Created by x86 on 11.01.2017.
 */

public class UseTestAssetsMasterDataTest extends Test {

    private Context context;
    private String fileNameInAssetsWithTestData;

    public UseTestAssetsMasterDataTest(Context context, String fileNameInAssetsWithTestData) {
        super();
        this.context = context;
        this.fileNameInAssetsWithTestData = fileNameInAssetsWithTestData;
    }

    @Override
    public Object model(int point, Object data, Object... args) {
        Object result = data;
        switch (point) {
            case Points.Splash.CHECK_NEED_LOAD_DATA_FROM_FILE:
                result = false;
                break;
            case Points.Splash.UPDATE_LOAD_DATA_URL_NO_VERSION:
                result = "test://no_data";
                break;
            case Points.Splash.UPDATE_LOADED_DATA_FROM_URL_NO_VERSION: {
                DataStorage dataStorage = new DataStorage();
                MasterData assetsData = dataStorage.loadFromAssets(context, fileNameInAssetsWithTestData);
                result = assetsData;
                if (assetsData != null && assetsData.recipes!=null && assetsData.recipes.size()>0) {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(assetsData.recipes.get(0).getId());
                    ids.add(assetsData.recipes.get(1).getId());
                    DataManager.get().setNowCookRecipes(ids);
                }

            }
            break;
        }
        return result;
    }
}
