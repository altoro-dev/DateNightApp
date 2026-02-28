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
    private int recipeId = -1;  // -1 means ADD mode, other value means EDIT mode
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialize database
        dbHelper = new RecipeDatabaseHelper(this);

        // Check if editing an existing recipe
        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        isEditMode = (recipeId != -1);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(isEditMode ? "Edit Recipe" : "Add New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        // Update button text based on mode
        saveButton.setText(isEditMode ? "💾 Save Changes" : "💾 Save Recipe");

        // Setup category spinner
        setupCategorySpinner();

        // If edit mode, load existing recipe data
        if (isEditMode) {
            loadRecipeData();
        }

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
        if (!isEditMode) {
            categorySpinner.setSelection(2);
        }
    }

    // Load recipe data from database and populate form (EDIT MODE only)
    private void loadRecipeData() {
        Recipe recipe = dbHelper.getRecipe(recipeId);

        if (recipe == null) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Populate form with existing data
        nameInput.setText(recipe.getName());
        descriptionInput.setText(recipe.getDescription());
        servingsInput.setText(String.valueOf(recipe.getServings()));
        prepTimeInput.setText(String.valueOf(recipe.getPrepTime()));
        cookTimeInput.setText(String.valueOf(recipe.getCookTime()));
        ingredientsInput.setText(recipe.getIngredients());
        instructionsInput.setText(recipe.getInstructions());

        if (recipe.getNotes() != null && !recipe.getNotes().isEmpty()) {
            notesInput.setText(recipe.getNotes());
        }

        // Set category spinner to current category
        String[] categories = {"Breakfast", "Lunch", "Dinner", "Dessert", "Snack"};
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(recipe.getCategory())) {
                categorySpinner.setSelection(i);
                break;
            }
        }
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

        // CREATE OR UPDATE based on mode
        if (isEditMode) {
            // UPDATE existing recipe
            Recipe recipe = dbHelper.getRecipe(recipeId);
            recipe.setName(name);
            recipe.setCategory(category);
            recipe.setDescription(description);
            recipe.setServings(servings);
            recipe.setPrepTime(prepTime);
            recipe.setCookTime(cookTime);
            recipe.setIngredients(ingredients);
            recipe.setInstructions(instructions);
            recipe.setNotes(notes.isEmpty() ? null : notes);

            int rowsAffected = dbHelper.updateRecipe(recipe);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Recipe updated successfully! ✅", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error updating recipe", Toast.LENGTH_SHORT).show();
            }

        } else {
            // ADD new recipe
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

            long id = dbHelper.addRecipe(recipe);

            if (id != -1) {
                Toast.makeText(this, "Recipe added successfully! 🎉", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving recipe", Toast.LENGTH_SHORT).show();
            }
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
