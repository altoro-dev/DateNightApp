package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

// Main Entry Point
public class HomeActivity extends AppCompatActivity {

    private TextView greetingText;
    private TextView dateText;
    private TextView foodSuggestion;
    private TextView movieSuggestion;
    private TextView gameSuggestion;
    private Button surpriseButton;
    private BottomNavigationView bottomNav;

    private DatabaseHelper dbHelper;
    private RecipeDatabaseHelper recipeDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        dateText = findViewById(R.id.dateText);
        foodSuggestion = findViewById(R.id.foodSuggestion);
        movieSuggestion = findViewById(R.id.movieSuggestion);
        gameSuggestion = findViewById(R.id.gameSuggestion);
        surpriseButton = findViewById(R.id.surpriseButton);
        bottomNav = findViewById(R.id.bottomNavigation);

        // Initialize databases
        dbHelper = new DatabaseHelper(this);
        recipeDbHelper = new RecipeDatabaseHelper(this);

        // Set greeting based on time of day
        setGreeting();

        // Set today's date
        setDate();

        // Generate initial suggestions
        generateSuggestions();

        // Surprise button - regenerate suggestions
        surpriseButton.setOnClickListener(v -> generateSuggestions());

        // Setup bottom navigation
        setupBottomNavigation();
    }

    // Set personalized greeting based on time of day
    private void setGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hour >= 5 && hour < 12) {
            greeting = "Good Morning ❤️";
        } else if (hour >= 12 && hour < 17) {
            greeting = "Good Afternoon ❤️";
        } else if (hour >= 17 && hour < 21) {
            greeting = "Good Evening ❤️";
        } else {
            greeting = "Good Night ❤️";
        }

        greetingText.setText(greeting);
    }

    // Display today's date
    private void setDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        dateText.setText(currentDate);
    }

    // Pick random suggestions for the day
    private void generateSuggestions() {
        // Get random food option
        List<DateOption> foodOptions = dbHelper.getOptionsByType("food");
        if (!foodOptions.isEmpty()) {
            DateOption randomFood = foodOptions.get(new Random().nextInt(foodOptions.size()));
            foodSuggestion.setText("🍝 " + randomFood.getName());
        } else {
            foodSuggestion.setText("🍝 Add food options in Decide");
        }

        // Get random movie/show option
        List<DateOption> watchOptions = dbHelper.getOptionsByType("watch");
        if (!watchOptions.isEmpty()) {
            DateOption randomWatch = watchOptions.get(new Random().nextInt(watchOptions.size()));
            movieSuggestion.setText("🎬 " + randomWatch.getName());
        } else {
            movieSuggestion.setText("🎬 Add watch options in Decide");
        }

        // Get random game option
        List<DateOption> gameOptions = dbHelper.getOptionsByType("game");
        if (!gameOptions.isEmpty()) {
            DateOption randomGame = gameOptions.get(new Random().nextInt(gameOptions.size()));
            gameSuggestion.setText("🎮 " + randomGame.getName());
        } else {
            gameSuggestion.setText("🎮 Add game options in Decide");
        }
    }

    // Setup bottom navigation bar
    private void setupBottomNavigation() {
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;

            } else if (itemId == R.id.nav_decide) {
                // Open Decide screen
                startActivity(new Intent(HomeActivity.this, DecideActivity.class));
                overridePendingTransition(0, 0); // No animation for smooth transition
                return true;

            } else if (itemId == R.id.nav_recipes) {
                // Open Recipe List
                startActivity(new Intent(HomeActivity.this, RecipeListActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_games) {
                // TODO: Games placeholder - Add games
                Toast.makeText(this, "Games coming soon! 🎮", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateSuggestions();
        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}