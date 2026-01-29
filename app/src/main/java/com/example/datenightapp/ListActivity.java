package com.example.datenightapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

// ListActivity - Main Activity for Displaying and Managing Date Options
public class ListActivity extends AppCompatActivity implements OptionAdapter.OnItemClickListener {
    // INSTANCE VARIABLES
    // Displays the scrollable list of options
    private RecyclerView recyclerView;

    // Bridges data (List) to views (RecyclerView)
    private OptionAdapter adapter;

    // Database manager for CRUD operations
    private DatabaseHelper dbHelper;

    // Determines which category to display
    private String type;

    // The current list of date options
    private List<DateOption> options;

    // TextView shown when list is empty
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // RETRIEVING DATA FROM INTENT
        type = getIntent().getStringExtra("TYPE");
        String title = getIntent().getStringExtra("TITLE");

        // SETUP TOOLBAR (ACTION BAR)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title); // "What to Do" or "What to Eat"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Shows <- back button

        // INITIALIZE DATABASE HELPER
        dbHelper = new DatabaseHelper(this);

        // SETUP RECYCLERVIEW
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Find empty state TextView
        emptyText = findViewById(R.id.emptyText);

        // Load data from database and populate RecyclerView
        loadOptions();

        // SETUP BUTTONS
        Button pickRandomButton = findViewById(R.id.pickRandomButton);
        Button addButton = findViewById(R.id.addButton);

        pickRandomButton.setOnClickListener(v -> pickRandomOption());
        addButton.setOnClickListener(v -> showAddDialog());
    }

    // loadOptions() - Loads data from database and updates UI
    private void loadOptions() {
        // Query database for options of current type
        options = dbHelper.getOptionsByType(type);

        // EMPTY STATE HANDLING
        if (options.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE); // Show "No options yet!"
            recyclerView.setVisibility(View.GONE); // Hide empty RecyclerView
        } else {
            emptyText.setVisibility(View.GONE); // Hide empty message
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
        }

        // CREATE AND SET ADAPTER
        adapter = new OptionAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    // pickRandomOption() - Randomly selects an option from the list
    private void pickRandomOption() {
        // Validation: Can't pick from empty list
        if (options.isEmpty()) {
            Toast.makeText(this, "Add some options first!", Toast.LENGTH_SHORT).show();
            return; // Exit method early
        }

        // RANDOM SELECTION
        Random random = new Random();
        DateOption randomOption = options.get(random.nextInt(options.size()));

        // ALERT DIALOG - Modal popup for displaying result
        new AlertDialog.Builder(this)
                .setTitle("🎉 Tonight's Pick!")
                .setMessage(randomOption.getName())
                .setPositiveButton("Perfect!", null) // null = just dismiss
                .setNeutralButton("Pick Again", (dialog, which) -> pickRandomOption())
                .show();
    }

    // showAddDialog() - Displays dialog for adding new option
    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Option");

        // CREATING EDITTEXT PROGRAMMATICALLY
        final EditText input = new EditText(this);
        input.setHint("Enter option name");
        input.setPadding(50, 20, 50, 20); // Pixels (better to use dp in XML)
        builder.setView(input); // Add EditText to dialog

        // POSITIVE BUTTON - "Add" button
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = input.getText().toString().trim();

            // INPUT VALIDATION - Always validate user input
            if (!name.isEmpty()) {
                // Add to database
                dbHelper.addOption(name, type);

                // Refresh the list to show new item
                loadOptions();

                // User feedback
                Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
            } else {
                // Show error if input is empty
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });

        // NEGATIVE BUTTON - "Cancel" button
        builder.setNegativeButton("Cancel", null);

        // Display the dialog
        builder.show();
    }

    // onEditClick() - Implements OnItemClickListener interface
    @Override
    public void onEditClick(DateOption option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Option");

        // Create EditText and pre-fill with current value
        final EditText input = new EditText(this);
        input.setText(option.getName());  // Pre-fill existing text
        input.setSelection(input.getText().length());  // Cursor at end
        input.setPadding(50, 20, 50, 20);
        builder.setView(input);

        // UPDATE BUTTON
        builder.setPositiveButton("Update", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                // Update in database using ID
                dbHelper.updateOption(option.getId(), name);

                // Refresh UI
                loadOptions();

                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // onDeleteClick() - Implements OnItemClickListener interface
    @Override
    public void onDeleteClick(DateOption option) {
        // CONFIRMATION DIALOG
        new AlertDialog.Builder(this)
                .setTitle("Delete Option")
                .setMessage("Are you sure you want to delete \"" + option.getName() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // User confirmed - delete from database
                    dbHelper.deleteOption(option.getId());

                    // Refresh UI (removes item from list)
                    loadOptions();

                    Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)  // User changed mind
                .show();
    }

    // onOptionsItemSelected() - Handles toolbar menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check which item was clicked
        if (item.getItemId() == android.R.id.home) {
            // finish() - Closes this activity
            finish();
            return true;
        }

        // We didn't handle it - let parent class try
        return super.onOptionsItemSelected(item);
    }

    /// POTENTIAL IMPROVEMENTS:
    /// 1. Add search functionality:
    ///    - SearchView in toolbar
    ///    - Filter options as user types
    /// 2. Sort options:
    ///    - By name (A-Z)
    ///    - By date added (newest first)
    ///    - By custom order (drag to reorder)
    /// 3. Swipe gestures:
    ///    - Swipe left to delete
    ///    - Swipe right to edit
    /// 4. Undo delete:
    ///    - Snackbar with undo option
    ///    - Temporarily keep deleted item in memory
    /// 5. Share functionality:
    ///    - Share option via text/email
    ///    - Share entire list
    /// 6. Import/Export:
    ///    - Backup to file
    ///    - Share between users
    /// 7. Categories/Tags:
    ///    - Sub-categories for food (Italian, Mexican, etc.)
    ///    - Filter by multiple tags
    /// 8. Animations:
    ///    - Animate item additions/deletions
    ///    - Ripple effects
    ///    - Custom transitions
    /// 9. Material Design 3:
    ///    - Use Material You components
    ///    - Dynamic color schemes
    /// 10. Accessibility:
    ///     - Content descriptions for screen readers
    ///     - Larger touch targets
    ///     - High contrast mode support
}
