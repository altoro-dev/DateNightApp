package com.example.datenightapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    // INSTANCE VARIABLES
    private List<DateOption> options;

    // Interface for handling click events
    private OnItemClickListener listener;

    // Custom interface for click callbacks
    public interface OnItemClickListener {
        void onEditClick(DateOption option);
        void onDeleteClick(DateOption option);
    }

    // Constructor - Creates a new adapter instance
    public OptionAdapter(List<DateOption> options, OnItemClickListener listener) {
        this.options = options;
        this.listener = listener;
    }

    // onCreateViewHolder - Creates new ViewHolders when needed
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_option, parent, false);

        // Wrap view in ViewHolder and return
        return new OptionViewHolder(view);
    }

    // onBindViewHolder - Binds data to an existing ViewHolder
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        // Get the DateOption for this position
        DateOption option = options.get(position);

        // Update the TextView with option name
        holder.itemText.setText(option.getName());

        // Click listeners - Attach to buttons
        holder.editButton.setOnClickListener(v -> listener.onEditClick(option));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(option));
    }

    // getItemCount() - Returns total number of items in list
    public int getItemCount() {
        return options.size();
    }

    // updateOptions() - Refreshes the list with new data
    public void updateOptions(List<DateOption> newOptions) {
        this.options = newOptions;

        // Notify RecyclerView that data has changed
        notifyDataSetChanged();
    }

    // OptionViewHolder - Holds references to views in each list item
    static class OptionViewHolder extends RecyclerView.ViewHolder {
        // View references - cached for fast access
        TextView itemText; // Displays option name
        ImageButton editButton; // Edit button
        ImageButton deleteButton; // Delete button

        // Constructor - Finds and caches all views
        public OptionViewHolder(@NonNull View itemView) {
            // Always call super with the item view
            super(itemView);

            // Find and cache all views using their IDs
            // These findViewByID calls happen ONCE per ViewHolder
            itemText = itemView.findViewById(R.id.itemText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    /// POTENTIAL IMPROVEMENTS:
    /// 1. Item click listener (click anywhere on item):
    ///    itemView.setOnClickListener(v -> listener.onItemClick(option));
    /// 2. Long press listener:
    ///    itemView.setOnLongClickListener(v -> {
    ///        listener.onItemLongClick(option);
    ///        return true;
    ///    });
    /// 3. Multiple view types (headers, items):
    ///    @Override
    ///    public int getItemViewType(int position) {
    ///        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    ///    }
    /// 4. Swipe to delete:
    ///    Use ItemTouchHelper.SimpleCallback
    /// 5. Drag to reorder:
    ///    Implement ItemTouchHelper for drag and drop
    /// 6. Selection mode:
    ///    Track selected items with Set<Integer>
    /// 7. DiffUtil for better performance:
    ///    Calculate minimal updates instead of notifyDataSetChanged()
    }
