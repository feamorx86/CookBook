package com.feamorx.cookebook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.feamorx.cookebook.R;
import com.feamorx.cookebook.model.Ingredient;
import com.feamorx.cookebook.model.Recipe;
import com.feamorx.cookebook.model.RecipeIngredient;
import com.feamorx.cookebook.utils.DataManager;

import java.util.ArrayList;

/**
 * Created by x86 on 11.01.2017.
 */

public class CookNowFragment extends Fragment {

    private TextView recipeNameTextView;

    private View addTimeButton;
    private TextView [] timerTextViews;

    private ViewGroup ingredientsLayout, actionsLayout;

    private ArrayList<RecipeNowActionViewHolder> actionViewHolders;
    private ArrayList<RecipeNowIngredientViewHolder> ingredientViewHolders;
    private Recipe recipe;

    public static CookNowFragment getInstance(Recipe recipe) {
        CookNowFragment fragment = new CookNowFragment();
        fragment.recipe = recipe;
        return fragment;
    }

    public CookNowFragment() {
        actionViewHolders = new ArrayList<>();
        ingredientViewHolders = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void clearRecipe() {
        recipeNameTextView.setText("");
        for(TextView timerText : timerTextViews) {
            timerText.setText("");
            timerText.setVisibility(View.GONE);
        }

        ingredientsLayout.removeAllViews();
        actionsLayout.removeAllViews();

        actionViewHolders.clear();
        ingredientViewHolders.clear();
    }

    private void showRecipe() {
        clearRecipe();

        if (recipe != null) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            recipeNameTextView.setText(recipe.getName());

            for(int i=0; i < recipe.getIngredients().size(); i++) {
                RecipeIngredient ingredient = recipe.getIngredients().get(i);
                View view = inflater.inflate(R.layout.recipe_now_ingredient_layout, null);
                RecipeNowIngredientViewHolder holder = new RecipeNowIngredientViewHolder();
                holder.setup(i, ingredient, view);
                ingredientsLayout.addView(view);
                ingredientViewHolders.add(holder);
            }

            for(int i=0; i < recipe.getDescriptions().size(); i++) {
                String action = recipe.getDescriptions().get(i);
                View view = inflater.inflate(R.layout.recipe_now_action_layout, null);
                RecipeNowActionViewHolder holder = new RecipeNowActionViewHolder();
                holder.setup(i, action, view);
                actionsLayout.addView(view);
                actionViewHolders.add(holder);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cook_now_fragment, container, false);
        recipeNameTextView = (TextView) view.findViewById(R.id.recipe_name_text_view);
        addTimeButton = view.findViewById(R.id.recipe_add_timer_button);

        ViewGroup timersLayout = (ViewGroup) view.findViewById(R.id.recipe_timers_layout);
        timerTextViews = new TextView[timersLayout.getChildCount()];
        for(int i=0; i < timersLayout.getChildCount(); i++) {
            timerTextViews[i] = (TextView) timersLayout.getChildAt(i);
        }

        ingredientsLayout = (ViewGroup) view.findViewById(R.id.recipe_ingredients_layout);
        actionsLayout = (ViewGroup) view.findViewById(R.id.recipe_actions_layout);

        if (recipe == null) {
            clearRecipe();
        } else {
            showRecipe();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clearRecipe();
    }

    private static class RecipeNowActionViewHolder {
        public TextView actionTextView;
        public boolean selected;
        public String action;
        public int position;

        public void setup(int position, String action, View view) {
            actionTextView = (TextView) view.findViewById(R.id.action);
            this.position = position;
            this.action = action;
            selected = false;

            String text = Integer.toString(position+1)+". "+action;
            actionTextView.setText(text);
        }
    }

    private static class RecipeNowIngredientViewHolder {
        public TextView numberTextView;
        public TextView ingredientTextView;
        public TextView countTextView;
        public RecipeIngredient ingredient;

        public void setup(int position, RecipeIngredient ingredient, View view) {
            this.ingredient = ingredient;
            numberTextView = (TextView) view.findViewById(R.id.number);
            ingredientTextView = (TextView) view.findViewById(R.id.ingredient_name);
            countTextView = (TextView) view.findViewById(R.id.ingredient_count);

            numberTextView.setText(Integer.toString(position+1));
            String ingredientName = ingredient.getIngredient() == null ? "<no ingredient>" : ingredient.getIngredient().getName();
            ingredientTextView.setText(ingredientName);

            String count = "";
            if (!TextUtils.isEmpty(ingredient.getStrCount())) {
                count = ingredient.getStrCount();
            }
            if (ingredient.getCount() != null) {
                count = count+" ("+String.format("%.2f", ingredient.getCount().doubleValue())+")";
            }
            countTextView.setText(count);
        }
    }
}
