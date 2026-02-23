package com.example.datenightapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages Recipe Database
 * Separate from DatabaseHelper
 */
public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    // Database Configuration
    private static final String DATABASE_NAME = "Recipes.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column Names
    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SERVINGS = "servings";
    private static final String COLUMN_PREP_TIME = "prepTime";
    private static final String COLUMN_COOK_TIME = "cookTime";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_INSTRUCTIONS = "instructions";
    private static final String COLUMN_NOTES = "notes";

    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create recipes table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_SERVINGS + " INTEGER,"
                + COLUMN_PREP_TIME + " INTEGER,"
                + COLUMN_COOK_TIME + " INTEGER,"
                + COLUMN_INGREDIENTS + " TEXT,"
                + COLUMN_INSTRUCTIONS + " TEXT,"
                + COLUMN_NOTES + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

        // Add default recipes
        addDefaultRecipes(db);
    }

    // Adds 5 starter recipes so users aren't greeted with empty list
    private void addDefaultRecipes(SQLiteDatabase db) {

        // Recipe 1: Spaghetti Carbonara
        insertRecipe(db,
                "Spaghetti Carbonara",
                "Dinner",
                "Classic Italian pasta with creamy egg sauce",
                2,  // servings
                10, // prep time
                15, // cook time
                "200g spaghetti\n100g pancetta or bacon\n2 large eggs\n50g Parmesan cheese, grated\n2 cloves garlic, minced\nSalt and black pepper\n2 tbsp olive oil",
                "1. Cook spaghetti in salted boiling water until al dente\n2. Meanwhile, fry pancetta in olive oil with garlic until crispy\n3. Beat eggs with grated Parmesan in a bowl\n4. Drain pasta, reserving 1 cup pasta water\n5. Remove pan from heat, add pasta to pancetta\n6. Quickly stir in egg mixture, adding pasta water to create creamy sauce\n7. Season with pepper and serve immediately",
                "The key is to remove from heat before adding eggs to prevent scrambling!"
        );

        // Recipe 2: Chocolate Chip Cookies
        insertRecipe(db,
                "Chocolate Chip Cookies",
                "Dessert",
                "Soft and chewy homemade cookies",
                24,  // servings (cookies)
                15,  // prep time
                12,  // cook time
                "2¼ cups all-purpose flour\n1 tsp baking soda\n1 tsp salt\n1 cup butter, softened\n¾ cup sugar\n¾ cup brown sugar\n2 large eggs\n2 tsp vanilla extract\n2 cups chocolate chips",
                "1. Preheat oven to 375°F (190°C)\n2. Mix flour, baking soda and salt in a bowl\n3. Beat butter and sugars until creamy\n4. Add eggs and vanilla, beat well\n5. Gradually stir in flour mixture\n6. Fold in chocolate chips\n7. Drop rounded tablespoons onto ungreased baking sheet\n8. Bake 9-11 minutes until golden brown\n9. Cool on baking sheet for 2 minutes, then transfer to wire rack",
                "For extra gooey cookies, slightly underbake them!"
        );

        // Recipe 3: Avocado Toast
        insertRecipe(db,
                "Avocado Toast",
                "Breakfast",
                "Simple and healthy breakfast",
                1,   // servings
                5,   // prep time
                5,   // cook time
                "1 ripe avocado\n2 slices bread\n1 tbsp olive oil\nSalt and pepper to taste\nRed pepper flakes (optional)\nFresh lemon juice\n1 egg (optional)",
                "1. Toast bread until golden and crispy\n2. Mash avocado with fork in a bowl\n3. Add lemon juice, salt, and pepper to avocado\n4. Spread avocado mixture generously on toast\n5. Drizzle with olive oil\n6. Sprinkle with red pepper flakes if desired\n7. Optional: top with fried or poached egg",
                "Add cherry tomatoes or feta cheese for extra flavor!"
        );

        // Recipe 4: Chicken Stir-Fry
        insertRecipe(db,
                "Chicken Stir-Fry",
                "Dinner",
                "Quick and colorful Asian-inspired dish",
                4,   // servings
                15,  // prep time
                10,  // cook time
                "500g chicken breast, sliced\n2 cups mixed vegetables (bell peppers, broccoli, carrots)\n3 tbsp soy sauce\n2 tbsp honey\n1 tbsp sesame oil\n2 cloves garlic, minced\n1 tbsp fresh ginger, grated\n2 tbsp vegetable oil\nCooked rice for serving",
                "1. Mix soy sauce, honey, and sesame oil in a small bowl for sauce\n2. Heat vegetable oil in large wok or pan over high heat\n3. Add chicken, cook until golden and cooked through (about 5 minutes)\n4. Remove chicken, set aside\n5. Add garlic and ginger to pan, cook 30 seconds\n6. Add vegetables, stir-fry for 3-4 minutes until tender-crisp\n7. Return chicken to pan\n8. Pour in sauce, toss everything together\n9. Cook for 2 more minutes\n10. Serve hot over rice",
                "Use high heat and keep ingredients moving for best results!"
        );

        // Recipe 5: Grilled Chicken Wrap
        insertRecipe(db,
                "Grilled Chicken Wrap",
                "Lunch",
                "Juicy grilled chicken with fresh veggies in a warm tortilla",
                4,   // servings (4 wraps)
                15,  // prep time
                15,  // cook time
                "2 large chicken breasts\n1 tbsp olive oil\n1 tsp garlic powder\n1 tsp paprika\nSalt and pepper to taste\n4 large flour tortillas\n1 cup shredded lettuce\n1 tomato, diced\n½ red onion, thinly sliced\n½ cup shredded cheddar cheese\n½ cup ranch or chipotle sauce",
                "1. Slice chicken breasts into thin strips\n2. Toss chicken with olive oil, garlic powder, paprika, salt, and pepper\n3. Heat skillet over medium-high heat\n4. Cook chicken 5-7 minutes until fully cooked and slightly charred\n5. Warm tortillas in a dry pan for 30 seconds per side\n6. Lay tortilla flat and add lettuce, tomato, onion, cheese, and chicken\n7. Drizzle with sauce\n8. Fold sides inward and roll tightly\n9. Slice in half and serve warm",
                "Let the chicken rest for 3-4 minutes before assembling to keep the wraps juicy!"
        );
    }

    // Helper method to insert a recipe during database creation
    private void insertRecipe(SQLiteDatabase db, String name, String category,
                              String description, int servings, int prepTime, int cookTime,
                              String ingredients, String instructions, String notes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_SERVINGS, servings);
        values.put(COLUMN_PREP_TIME, prepTime);
        values.put(COLUMN_COOK_TIME, cookTime);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_INSTRUCTIONS, instructions);
        values.put(COLUMN_NOTES, notes);
        db.insert(TABLE_RECIPES, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, simple approach: drop and recreate
        // In production, you'd want to preserve user data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // ========== CRUD OPERATIONS ==========

    // CREATE - Add a new recipe to database
    public long addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_CATEGORY, recipe.getCategory());
        values.put(COLUMN_DESCRIPTION, recipe.getDescription());
        values.put(COLUMN_SERVINGS, recipe.getServings());
        values.put(COLUMN_PREP_TIME, recipe.getPrepTime());
        values.put(COLUMN_COOK_TIME, recipe.getCookTime());
        values.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        values.put(COLUMN_INSTRUCTIONS, recipe.getInstructions());
        values.put(COLUMN_NOTES, recipe.getNotes());

        long id = db.insert(TABLE_RECIPES, null, values);
        db.close();
        return id;
    }

    // READ - Get all recipes from database, sorted by name
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_RECIPES + " ORDER BY " + COLUMN_NAME + " ASC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(0),      // id
                        cursor.getString(1),   // name
                        cursor.getString(2),   // category
                        cursor.getString(3),   // description
                        cursor.getInt(4),      // servings
                        cursor.getInt(5),      // prepTime
                        cursor.getInt(6),      // cookTime
                        cursor.getString(7),   // ingredients
                        cursor.getString(8),   // instructions
                        cursor.getString(9)    // notes
                );
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipes;
    }

    // Get a single recipe by ID
    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES,
                null,  // all columns
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Recipe recipe = null;
        if (cursor.moveToFirst()) {
            recipe = new Recipe(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                    cursor.getInt(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9)
            );
        }

        cursor.close();
        db.close();
        return recipe;
    }

    // UPDATE - Update an existing recipe
    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_CATEGORY, recipe.getCategory());
        values.put(COLUMN_DESCRIPTION, recipe.getDescription());
        values.put(COLUMN_SERVINGS, recipe.getServings());
        values.put(COLUMN_PREP_TIME, recipe.getPrepTime());
        values.put(COLUMN_COOK_TIME, recipe.getCookTime());
        values.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        values.put(COLUMN_INSTRUCTIONS, recipe.getInstructions());
        values.put(COLUMN_NOTES, recipe.getNotes());

        int result = db.update(TABLE_RECIPES, values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(recipe.getId())});

        db.close();
        return result;
    }

    // DELETE - Delete a recipe
    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Search recipes by name or description
    public List<Recipe> searchRecipes(String query) {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_NAME + " LIKE ? OR " + COLUMN_DESCRIPTION + " LIKE ?";
        String searchPattern = "%" + query + "%";

        Cursor cursor = db.query(TABLE_RECIPES,
                null,
                selection,
                new String[]{searchPattern, searchPattern},
                null, null,
                COLUMN_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getInt(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9)
                );
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipes;
    }

    // Get total number of recipes
    public int getRecipeCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_RECIPES, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
}
