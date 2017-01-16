package com.feamorx.cookebook.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.feamorx.cookebook.R;
import com.feamorx.cookebook.fragments.CookNowFragment;
import com.feamorx.cookebook.fragments.MainScreenFragment;
import com.feamorx.cookebook.model.Recipe;
import com.feamorx.cookebook.utils.DataManager;

/**
 * Created by x86 on 11.01.2017.
 */

public class RecipeDetailsActivity extends AppCompatActivity {
    public static class DetailType {
        public static final int SHOW_RECIPE_DEFAULT = 9;
        public static final int COOK_NOW_DETAIL = 10;
    }

    public static final String EXTRA_DETAIL_TYPE = "detail_type";
    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        int detailType = getIntent().getIntExtra(EXTRA_DETAIL_TYPE, DetailType.SHOW_RECIPE_DEFAULT);
        Recipe recipe = null;
        if (getIntent().hasExtra(EXTRA_RECIPE_ID)) {
            int recipeId = getIntent().getExtras().getInt(EXTRA_RECIPE_ID);
            if (DataManager.get().getMasterData() != null) {
                recipe = DataManager.get().getMasterData().getRecipe(recipeId);
            }
        }

        switch (detailType) {
            case DetailType.COOK_NOW_DETAIL: {
                if (recipe != null) {
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment cookNowFragment = CookNowFragment.getInstance(recipe);
                    transaction.add(R.id.recipe_detail_content_layout, cookNowFragment, "cookNowDetail");
                    transaction.commit();
                } else {
                    //TODO: show error
                    finish();
                }
            }
            break;
            case DetailType.SHOW_RECIPE_DEFAULT:
                break;
            default:
                //TODO: show error
                finish();
                break;
        }
    }
}
