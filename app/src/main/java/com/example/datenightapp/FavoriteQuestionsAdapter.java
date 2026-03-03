package com.example.datenightapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for displaying favorite questions in DeepQuestionsGame
public class FavoriteQuestionsAdapter extends RecyclerView.Adapter<FavoriteQuestionsAdapter.ViewHolder> {

    private List<DeepQuestions> questions;
    private DeepQuestionsHelper dbHelper;
    private Runnable onFavoriteRemoved;

    public FavoriteQuestionsAdapter(List<DeepQuestions> questions, DeepQuestionsHelper dbHelper, Runnable onFavoriteRemoved) {
        this.questions = questions;
        this.dbHelper = dbHelper;
        this.onFavoriteRemoved = onFavoriteRemoved;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeepQuestions question = questions.get(position);

        holder.questionText.setText(question.getQuestion());
        holder.levelBadge.setText(getLevelEmoji(question.getLevel()));

        // Remove favorite button
        holder.removeButton.setOnClickListener(v -> {
            dbHelper.toggleFavorite(question.getId());
            Toast.makeText(v.getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            onFavoriteRemoved.run();
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    /**
     * Get emoji for level
     */
    private String getLevelEmoji(String level) {
        switch (level) {
            case "icebreaker":
                return "🧊 Ice Breaker";
            case "deep":
                return "💙 Deep";
            case "deeper":
                return "❤️‍🔥 Deeper";
            default:
                return level;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        TextView levelBadge;
        ImageButton removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            levelBadge = itemView.findViewById(R.id.levelBadge);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
