package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// MainActivity - The entry point of the Date Night app
// Home screen that displays the main options
public class DecideActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    // onCreate() - Activity Lifecycle Method
    // LIFECYCLE: onCreate() -> onStart() -> onResume() (activity visible)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super.onCreate() first - required by Android framework
        super.onCreate(savedInstanceState);

        // inflates the XML layout and displays it on screen
        setContentView(R.layout.activity_decide);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Decide");

        // Find all category cards
        CardView activitiesCard = findViewById(R.id.activitiesCard);
        CardView foodCard = findViewById(R.id.foodCard);
        CardView watchCard = findViewById(R.id.watchCard);
        CardView gameCard = findViewById(R.id.gameCard);

        // Click Listener for Activities Card
        activitiesCard.setOnClickListener(v -> {
            Intent intent = new Intent(DecideActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "activity");
            intent.putExtra("TITLE", "What to Do");
            startActivity(intent);
        });

        // Click Listener for Food Card
        foodCard.setOnClickListener(v -> {
            Intent intent = new Intent(DecideActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "food");
            intent.putExtra("TITLE", "What to Eat");
            startActivity(intent);
        });

        // Click Listener for Watch Card
        watchCard.setOnClickListener(v -> {
            Intent intent = new Intent(DecideActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "watch");
            intent.putExtra("TITLE", "What to Watch");
            startActivity(intent);
        });

        // Click Listener for Game Card
        gameCard.setOnClickListener(v -> {
            Intent intent = new Intent(DecideActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "game");
            intent.putExtra("TITLE", "Game Night");
            startActivity(intent);
        });

        // Setup bottom navigation
        bottomNav = findViewById(R.id.bottomNavigation);
        setupBottomNavigation();
    }

    // Setup bottom navigation with Decide tab selected
    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_decide);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(DecideActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;

            } else if (itemId == R.id.nav_decide) {
                return true;

            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(DecideActivity.this, RecipeListActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_games) {
                startActivity(new Intent(this, GameListActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNav.setSelectedItemId(R.id.nav_decide);
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