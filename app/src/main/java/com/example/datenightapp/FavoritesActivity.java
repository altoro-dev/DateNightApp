package com.example.datenightapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Shows all favorited questions in DeepQuestionsGame
public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyText;
    private DeepQuestionsHelper dbHelper;
    private FavoriteQuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favorite Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new DeepQuestionsHelper(this);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        emptyText = findViewById(R.id.emptyText);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load favorites
        loadFavorites();
    }

    // Load all favorite questions
    private void loadFavorites() {
        List<DeepQuestions> favorites = dbHelper.getFavoriteQuestions();

        if (favorites.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new FavoriteQuestionsAdapter(favorites, dbHelper, this::loadFavorites);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites(); // Refresh when returning
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
