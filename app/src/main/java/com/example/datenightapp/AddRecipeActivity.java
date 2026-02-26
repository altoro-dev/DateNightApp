package com.example.datenightapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// AddRecipeActivity - Form to add new custom recipes
public class AddRecipeActivity extends AppCompatActivity {

    private EditText nameInput;
    private Spinner categorySpinner;
    private EditText descriptionInput;
    private EditText servingsInput;
    private EditText prepTimeInput;
    private EditText cookTimeInput;
    private EditText ingredientsInput;
    private EditText instructionsInput;
    private EditText notesInput;
    private Button saveButton;

    private RecipeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize database
        dbHelper = new RecipeDatabaseHelper(this);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        descriptionInput = findViewById(R.id.descriptionInput);
        servingsInput = findViewById(R.id.servingsInput);
        prepTimeInput = findViewById(R.id.prepTimeInput);
        cookTimeInput = findViewById(R.id.cookTimeInput);
        ingredientsInput = findViewById(R.id.ingredientsInput);
        instructionsInput = findViewById(R.id.instructionsInput);
        notesInput = findViewById(R.id.notesInput);
        saveButton = findViewById(R.id.saveButton);

        // Setup category spinner
        setupCategorySpinner();

        // Save button click
        saveButton.setOnClickListener(v -> saveRecipe());
    }

    // Setup category dropdown with predefined options
    private void setupCategorySpinner() {
        String[] categories = {"Breakfast", "Lunch", "Dinner", "Dessert", "Snack"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set default to Dinner
        categorySpinner.setSelection(2);
    }

    // Validate input and save recipe to database
    private void saveRecipe() {
        // Get input values
        String name = nameInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String description = descriptionInput.getText().toString().trim();
        String servingsStr = servingsInput.getText().toString().trim();
        String prepTimeStr = prepTimeInput.getText().toString().trim();
        String cookTimeStr = cookTimeInput.getText().toString().trim();
        String ingredients = ingredientsInput.getText().toString().trim();
        String instructions = instructionsInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        // Validate required fields
        if (name.isEmpty()) {
            nameInput.setError("Recipe name is required");
            nameInput.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            descriptionInput.setError("Description is required");
            descriptionInput.requestFocus();
            return;
        }

        if (servingsStr.isEmpty()) {
            servingsInput.setError("Servings is required");
            servingsInput.requestFocus();
            return;
        }

        if (prepTimeStr.isEmpty()) {
            prepTimeInput.setError("Prep time is required");
            prepTimeInput.requestFocus();
            return;
        }

        if (cookTimeStr.isEmpty()) {
            cookTimeInput.setError("Cook time is required");
            cookTimeInput.requestFocus();
            return;
        }

        if (ingredients.isEmpty()) {
            ingredientsInput.setError("Ingredients are required");
            ingredientsInput.requestFocus();
            return;
        }

        if (instructions.isEmpty()) {
            instructionsInput.setError("Instructions are required");
            instructionsInput.requestFocus();
            return;
        }

        // Parse numeric values
        int servings;
        int prepTime;
        int cookTime;

        try {
            servings = Integer.parseInt(servingsStr);
            prepTime = Integer.parseInt(prepTimeStr);
            cookTime = Integer.parseInt(cookTimeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate numeric ranges
        if (servings <= 0) {
            servingsInput.setError("Must be greater than 0");
            servingsInput.requestFocus();
            return;
        }

        if (prepTime < 0) {
            prepTimeInput.setError("Cannot be negative");
            prepTimeInput.requestFocus();
            return;
        }

        if (cookTime < 0) {
            cookTimeInput.setError("Cannot be negative");
            cookTimeInput.requestFocus();
            return;
        }

        // Create recipe object
        Recipe recipe = new Recipe(
                name,
                category,
                description,
                servings,
                prepTime,
                cookTime,
                ingredients,
                instructions,
                notes.isEmpty() ? null : notes
        );

        // Save to database
        long id = dbHelper.addRecipe(recipe);

        if (id != -1) {
            Toast.makeText(this, "Recipe added successfully! 🎉", Toast.LENGTH_SHORT).show();
            finish(); // Return to recipe list
        } else {
            Toast.makeText(this, "Error saving recipe", Toast.LENGTH_SHORT).show();
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
