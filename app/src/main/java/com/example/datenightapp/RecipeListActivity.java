package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

// Displays list of all recipes
public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecipeDatabaseHelper dbHelper;
    private List<Recipe> recipes;
    private TextView emptyText;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new RecipeDatabaseHelper(this);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyText = findViewById(R.id.emptyText);

        // Load recipes from database
        loadRecipes();

        // Setup bottom navigation
        bottomNav = findViewById(R.id.bottomNavigation);
        setupBottomNavigation();
    }

    // Load all recipes from database and display them
    private void loadRecipes() {
        recipes = dbHelper.getAllRecipes();

        // Show empty state if no recipes
        if (recipes.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        // Setup adapter with click listener
        adapter = new RecipeAdapter(recipes, recipe -> {
            // Open recipe detail screen
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", recipe.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    // Setup bottom navigation
    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_recipes);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(RecipeListActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_decide) {
                startActivity(new Intent(RecipeListActivity.this, DecideActivity.class));
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
        loadRecipes();
        bottomNav.setSelectedItemId(R.id.nav_recipes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
