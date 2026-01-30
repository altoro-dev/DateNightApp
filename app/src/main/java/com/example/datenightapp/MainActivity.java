package com.example.datenightapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

// MainActivity - The entry point of the Date Night app
// Home screen that displays the main options
public class MainActivity extends AppCompatActivity {

    // onCreate() - Activity Lifecycle Method
    // LIFECYCLE: onCreate() -> onStart() -> onResume() (activity visible)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super.onCreate() first - required by Android framework
        super.onCreate(savedInstanceState);

        // setContentView() inflates the XML layout and displays it on screen
        setContentView(R.layout.activity_main);

        // findViewById() locates views from the layout by their ID
        CardView activitiesCard = findViewById(R.id.activitiesCard);
        CardView foodCard = findViewById(R.id.foodCard);
        CardView watchCard = findViewById(R.id.watchCard);
        CardView gameCard = findViewById(R.id.gameCard);

        // Click Listener for Activities Card
        activitiesCard.setOnClickListener(v -> {
            // Create explicit intent: (current context, target activity class)
            Intent intent = new Intent(MainActivity.this, ListActivity.class);

            // Add extra data - TYPE determines which data to load from database
            intent.putExtra("TYPE", "activity");

            // Add extra data - TITLE determines the toolbar title
            intent.putExtra("TITLE", "What to Do");

            // Start the new activity
            startActivity(intent);
        });

        // Click Listener for Food Card
        foodCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "food");
            intent.putExtra("TITLE", "What to Eat");
            startActivity(intent);
        });

        // Click Listener for Watch Card
        watchCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "watch");
            intent.putExtra("TITLE", "What to Watch");
            startActivity(intent);
        });

        // Click Listener for Game Card
        gameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("TYPE", "game");
            intent.putExtra("TITLE", "Game Night");
            startActivity(intent);
        });
    }
}