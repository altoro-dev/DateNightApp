package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Main screen for Deep Questions game
public class DeepQuestionsActivity extends AppCompatActivity {

    private CardView iceBreakerCard;
    private CardView deepCard;
    private CardView deeperCard;

    private TextView iceBreakerProgress;
    private TextView deepProgress;
    private TextView deeperProgress;
    private TextView totalProgress;

    private DeepQuestionsHelper dbHelper;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_questions);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Let's Get Deep");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new DeepQuestionsHelper(this);

        // Initialize views
        iceBreakerCard = findViewById(R.id.iceBreakerCard);
        deepCard = findViewById(R.id.deepCard);
        deeperCard = findViewById(R.id.deeperCard);

        iceBreakerProgress = findViewById(R.id.iceBreakerProgress);
        deepProgress = findViewById(R.id.deepProgress);
        deeperProgress = findViewById(R.id.deeperProgress);
        totalProgress = findViewById(R.id.totalProgress);

        bottomNav = findViewById(R.id.bottomNavigation);

        // Update progress displays
        updateProgress();

        // Card click listeners
        iceBreakerCard.setOnClickListener(v -> openLevel("icebreaker", "Ice Breaker 🧊"));
        deepCard.setOnClickListener(v -> openLevel("deep", "Deep 💙"));
        deeperCard.setOnClickListener(v -> openLevel("deeper", "Deeper ❤️‍🔥"));

        // Setup bottom navigation
        setupBottomNavigation();
    }

    // Update progress text for all levels
    private void updateProgress() {
        int iceCount = dbHelper.getProgress("icebreaker");
        int deepCount = dbHelper.getProgress("deep");
        int deeperCount = dbHelper.getProgress("deeper");
        int total = iceCount + deepCount + deeperCount;

        iceBreakerProgress.setText(iceCount + "/100");
        deepProgress.setText(deepCount + "/100");
        deeperProgress.setText(deeperCount + "/100");
        totalProgress.setText("Total Progress: " + total + "/300 questions");
    }

    // Open question display for selected level
    private void openLevel(String level, String title) {
        Intent intent = new Intent(DeepQuestionsActivity.this, QuestionDisplayActivity.class);
        intent.putExtra("LEVEL", level);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    // Setup bottom navigation
    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(DeepQuestionsActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_decide) {
                startActivity(new Intent(DeepQuestionsActivity.this, DecideActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(DeepQuestionsActivity.this, RecipeListActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (itemId == R.id.nav_games) {
                return true;
            }

            return false;
        });

        bottomNav.setSelectedItemId(R.id.nav_games);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh progress when returning from questions
        updateProgress();
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
