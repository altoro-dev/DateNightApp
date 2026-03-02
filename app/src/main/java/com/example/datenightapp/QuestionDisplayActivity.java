package com.example.datenightapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// Display questions for selected level
public class QuestionDisplayActivity extends AppCompatActivity {

    private TextView levelTitle;
    private TextView progressText;
    private TextView questionText;
    private Button nextButton;
    private ImageButton favoriteButton;

    private DeepQuestionsHelper dbHelper;
    private String level;
    private String title;
    private DeepQuestions currentQuestion;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);

        // Get level and title from intent
        level = getIntent().getStringExtra("LEVEL");
        title = getIntent().getStringExtra("TITLE");

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new DeepQuestionsHelper(this);

        // Initialize views
        levelTitle = findViewById(R.id.levelTitle);
        progressText = findViewById(R.id.progressText);
        questionText = findViewById(R.id.questionText);
        nextButton = findViewById(R.id.nextButton);
        favoriteButton = findViewById(R.id.favoriteButton);

        levelTitle.setText(title);

        // Load first/current question
        loadCurrentQuestion();

        // Button listeners
        nextButton.setOnClickListener(v -> nextQuestion());
        favoriteButton.setOnClickListener(v -> toggleFavorite());
    }

    // Load current question for this level
    private void loadCurrentQuestion() {
        // Get current question (continues where left off)
        currentQuestion = dbHelper.getCurrentQuestion(level);

        if (currentQuestion == null) {
            // All questions completed!
            questionText.setText("🎉 Congratulations!\n\nYou've discussed all 100 questions in this level!\n\nTap the back button to choose another level.");
            nextButton.setEnabled(false);
            favoriteButton.setVisibility(View.GONE);
            return;
        }

        displayQuestion();
    }

    // Display current question
    private void displayQuestion() {
        questionText.setText(currentQuestion.getQuestion());

        // Update progress
        int progress = dbHelper.getProgress(level);
        progressText.setText(progress + "/100");

        // Update favorite button
        updateFavoriteButton();
    }

    // Move to next question
    private void nextQuestion() {
        dbHelper.markAsDiscussed(currentQuestion.getId());

        currentQuestion = dbHelper.getNextQuestion(level);

        if (currentQuestion == null) {
            questionText.setText("🎉 Congratulations!\n\nYou've discussed all 100 questions in this level!\n\nTap the back button to choose another level.");
            nextButton.setEnabled(false);
            favoriteButton.setVisibility(View.GONE);
            progressText.setText("100/100");
        } else {
            displayQuestion();
        }
    }

    // Toggle favorite status
    private void toggleFavorite() {
        dbHelper.toggleFavorite(currentQuestion.getId());

        // Reload question to get updated favorite status
        currentQuestion = dbHelper.getQuestion(currentQuestion.getId());
        updateFavoriteButton();

        String message = currentQuestion.isFavorite() ? "Added to favorites! ⭐" : "Removed from favorites";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Update favorite button appearance
    private void updateFavoriteButton() {
        if (currentQuestion.isFavorite()) {
            favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
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
