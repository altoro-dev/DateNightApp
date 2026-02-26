package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Shows full recipe details
public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeName;
    private TextView recipeCategory;
    private TextView recipeServings;
    private TextView recipePrepTime;
    private TextView recipeCookTime;
    private TextView recipeTotalTime;
    private TextView recipeDescription;
    private TextView recipeIngredients;
    private TextView recipeInstructions;
    private TextView recipeNotes;

    private RecipeDatabaseHelper dbHelper;
    private BottomNavigationView bottomNav;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new RecipeDatabaseHelper(this);

        // Initialize view
        recipeName = findViewById(R.id.recipeName);
        recipeCategory = findViewById(R.id.recipeCategory);
        recipeServings = findViewById(R.id.recipeServings);
        recipePrepTime = findViewById(R.id.recipePrepTime);
        recipeCookTime = findViewById(R.id.recipeCookTime);
        recipeTotalTime = findViewById(R.id.recipeTotalTime);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeInstructions = findViewById(R.id.recipeInstructions);
        recipeNotes = findViewById(R.id.recipeNotes);
        bottomNav = findViewById(R.id.bottomNavigation);

        // Get recipe ID from intent
        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);

        if (recipeId == -1) {
            Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRecipe();
        setupBottomNavigation();
    }

    // Load recipe from database and display all details
    private void loadRecipe() {
        Recipe recipe = dbHelper.getRecipe(recipeId);

        if(recipe == null) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set toolbar title
        getSupportActionBar().setTitle(recipe.getName());

        // Display recipe details
        recipeName.setText(recipe.getName());
        recipeCategory.setText(recipe.getCategory());
        recipeDescription.setText(recipe.getDescription());

        // Display time information
        recipeServings.setText(recipe.getServings() + " servings");
        recipePrepTime.setText("Prep: " + recipe.getPrepTime() + " min");
        recipeCookTime.setText("Cook: " + recipe.getCookTime() + " min");
        recipeTotalTime.setText("Total: " + recipe.getTotalTime() + " min");

        // Display ingredients and instructions
        recipeIngredients.setText(recipe.getIngredients());
        recipeInstructions.setText(recipe.getInstructions());

        // Display notes if any
        if (recipe.getNotes() != null && !recipe.getNotes().isEmpty()) {
            recipeNotes.setText(recipe.getNotes());
        } else {
            recipeNotes.setText("No additional notes");
        }
    }

    // Create options menu with delete button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // Add delete option to toolbar menu
        menu.add(0, 1, 0, "Delete")
                .setIcon(android.R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    // Show confirmation dialog before deleting recipe
    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recipe?")
                .setMessage("Are you sure you want to delete this recipe? This cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // User confirmed - delete the recipe
                    deleteRecipe();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // User cancelled - do nothing
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Delete the recipe from database and return to list
    private void deleteRecipe() {
        dbHelper.deleteRecipe(recipeId);
        Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
        finish(); // Return to recipe list
    }

    // Setup bottom navigation
    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_recipes);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(RecipeDetailActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_decide) {
                startActivity(new Intent(RecipeDetailActivity.this, DecideActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_recipes) {
                return true;
            } else if (itemId == R.id.nav_games) {
                Toast.makeText(this, "Games coming soon!", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNav.setSelectedItemId(R.id.nav_recipes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == 1) {  // Delete menu item
            showDeleteConfirmation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
