package com.example.datenightapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

// Connects recipe data to RecyclerView
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private OnRecipeClickListener listener;

    // Handles recipe clicks
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        // Set recipe data to views
        holder.nameText.setText(recipe.getName());
        holder.descriptionText.setText(recipe.getDescription());
        holder.categoryText.setText(recipe.getCategory());

        // Format time and servings string
        String timeInfo = recipe.getTotalTime() + " min " + recipe.getServings() + "servings";
        holder.timeText.setText(timeInfo);

        // Handle click on entire card
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // Update recipe list and refresh display
    public void updateRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }

    // Caches view references for performance
    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView descriptionText;
        TextView categoryText;
        TextView timeText;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.recipeName);
            descriptionText = itemView.findViewById(R.id.recipeDescription);
            categoryText = itemView.findViewById(R.id.recipeCategory);
            timeText = itemView.findViewById(R.id.recipeTime);
        }
    }
}
