package com.example.datenightapp;

public class DateOption {
    private int id;
    private String name;
    private String type;

    // Constructor - Creates a new DateOption object
    public DateOption(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // GETTERS - Public methods to read private fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    // SETTERS - Public methods to modify private fields
    public void setId(int id) {
        // TODO: Add validation here
        this.id = id;
    }

    public void setName(String name) {
        // TODO: Add validation here
        this.name = name;
    }

    public void setType(String type) {
        // TODO: Validate type is either "activity" or "food"
        this.type = type;
    }

    /// POTENTIAL IMPROVEMENTS:
    /// 1. Use an enum for type:
    ///    public enum OptionType { ACTIVITY, FOOD }
    ///    private OptionType type;
    /// 2. Add more fields:
    ///    private String imageUrl;
    ///    private int rating;
    ///    private Date lastUsed;
    /// 3. Implement Parcelable for passing between activities:
    ///    implements Parcelable
    /// 4. Add a Builder pattern for complex construction
    /// 5. Make it immutable (final fields, no setters) for thread safety
}
