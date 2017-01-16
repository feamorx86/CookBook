package com.feamorx.cookebook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feamorx.cookebook.R;
import com.feamorx.cookebook.activities.RecipeDetailsActivity;
import com.feamorx.cookebook.model.Recipe;
import com.feamorx.cookebook.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x86 on 11.01.2017.
 */

public class CookNowListFragment extends ListFragment {

    public CookNowListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<Recipe> nowCookRecipes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cook_now_list_fragment, container, false);

        nowCookRecipes.clear();
        if (DataManager.get().getNowCookRecipes() !=null && DataManager.get().getNowCookRecipes().size() > 0) {
            for(Integer id : DataManager.get().getNowCookRecipes()) {
                Recipe recipe = DataManager.get().getMasterData().getRecipe(id);
                nowCookRecipes.add(recipe);
            }
        }

        CookNowAdapter adapter = new CookNowAdapter(getContext(), R.layout.recipe_now_list_layout, nowCookRecipes);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        Recipe recipe = (Recipe) getListAdapter().getItem(position);
        Intent intent = new Intent();
        intent.setClass(getContext(), RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.EXTRA_DETAIL_TYPE, RecipeDetailsActivity.DetailType.COOK_NOW_DETAIL);
        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }

    private class CookNowListItemHolder {
        public Recipe recipe;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ViewGroup timersLayout;
        public TextView[] timers;

        public void initialize(View view) {
            titleTextView = (TextView) view.findViewById(R.id.list_item_title);
            descriptionTextView = (TextView) view.findViewById(R.id.list_item_content);
            timersLayout = (ViewGroup) view.findViewById(R.id.list_item_time_layout);
            ViewGroup timersContainer = (ViewGroup) view.findViewById(R.id.list_item_timer_views_container);
            timers = new TextView[timersContainer.getChildCount()];
            for(int i=0; i<timersContainer.getChildCount(); i++) {
                timers[i] = (TextView) timersContainer.getChildAt(i);
            }
            view.setTag(this);
        }

        public void setup(Recipe recipe) {
            this.recipe = recipe;
            titleTextView.setText(recipe.getName());
            StringBuilder descriptionBuilder = new StringBuilder();
            for(String description : recipe.getDescriptions()) {
                descriptionBuilder.append(description+", ");
            }
            descriptionTextView.setText(descriptionBuilder.toString());
            //TODO: add timers setup
            timersLayout.setVisibility(View.GONE);
        }
    }

    private class CookNowAdapter extends  ArrayAdapter<Recipe> {

        public CookNowAdapter(Context context, int resource, List<Recipe> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Recipe recipe = getItem(position);
            CookNowListItemHolder holder;
            View view;
            if (convertView == null) {
                view = View.inflate(getContext(), R.layout.recipe_now_list_layout, null);
                holder = new CookNowListItemHolder();
                holder.initialize(view);
            } else {
                view = convertView;
                holder = (CookNowListItemHolder) convertView.getTag();
            }
            holder.setup(recipe);
            return view;
        }
    }

}
