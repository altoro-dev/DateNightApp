package com.example.datenightapp;

// Model class for Deep Questions Game
// Represents a single question with its level, content, and status
public class DeepQuestions {

    private int id;
    private String level;  // "icebreaker", "deep", "deeper"
    private String question;
    private boolean isDiscussed;
    private boolean isFavorite;
    private int orderIndex;  // 1-100 within each level

    // Full constructor
    public DeepQuestions(int id, String level, String question,
                        boolean isDiscussed, boolean isFavorite, int orderIndex) {
        this.id = id;
        this.level = level;
        this.question = question;
        this.isDiscussed = isDiscussed;
        this.isFavorite = isFavorite;
        this.orderIndex = orderIndex;
    }

    // Simple constructor to create new questions
    public DeepQuestions(String level, String question, int orderIndex) {
        this(0, level, question, false, false, orderIndex);
    }

    // Getters
    public int getId() { return id; }
    public String getLevel() { return level; }
    public String getQuestion() { return question; }
    public boolean isDiscussed() { return isDiscussed; }
    public boolean isFavorite() { return isFavorite; }
    public int getOrderIndex() { return orderIndex; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setLevel(String level) { this.level = level; }
    public void setQuestion(String question) { this.question = question; }
    public void setDiscussed(boolean discussed) { this.isDiscussed = discussed; }
    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
}
