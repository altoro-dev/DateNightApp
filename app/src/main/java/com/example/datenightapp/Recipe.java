package com.example.datenightapp;

public class Recipe {
    private int id;
    private String name;
    private String category;
    private String description;
    private int servings;
    private int prepTime;
    private int cookTime;
    private String ingredients;
    private String instructions;
    private String notes;

    // Full Constructor - Used when loading from database
    public Recipe(int id, String name, String category, String description,
                  int servings, int prepTime, int cookTime,
                  String ingredients, String instructions, String notes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.servings = servings;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.notes = notes;
    }

    // Simple Constructor - Used when creating new recipes
    public Recipe(String name, String category, String description,
                  int servings, int prepTime, int cookTime,
                  String ingredients, String instructions, String notes) {
        this(0, name, category, description, servings, prepTime, cookTime,
                ingredients, instructions, notes);
    }

    // GETTERS - Methods to read private fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getServings() {
        return servings;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    //Added method to get total time
    public int getTotalTime() {
        return prepTime + cookTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getNotes() {
        return notes;
    }

    // SETTERS - Methods to update private fields
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // For debugging - shows recipe summary when printed
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", servings=" + servings +
                ", totalTime=" + getTotalTime() + " min" +
                '}';
    }
}