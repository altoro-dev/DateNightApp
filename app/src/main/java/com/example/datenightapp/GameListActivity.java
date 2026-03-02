package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;
import android.view.View;

// Shows list of available games
public class GameListActivity extends AppCompatActivity {

    private CardView deepQuestionsCard;
    // TODO: Add more game cards here
    // TODO: private CardView ticTacToeCard;

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Games");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize game cards
        deepQuestionsCard = findViewById(R.id.deepQuestionsCard);

        // Setup click listeners
        deepQuestionsCard.setOnClickListener(v -> {
            Intent intent = new Intent(GameListActivity.this, DeepQuestionsActivity.class);
            startActivity(intent);
        });

        // Setup bottom navigation
        bottomNav = findViewById(R.id.bottomNavigation);
        setupBottomNavigation();
    }

    // Setup bottom navigation
    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_games);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(GameListActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_decide) {
                startActivity(new Intent(GameListActivity.this, DecideActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(GameListActivity.this, RecipeListActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_games) {
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNav.setSelectedItemId(R.id.nav_games);
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
