package com.example.elementsfoodapp.ui.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.elementsfoodapp.Food;
import com.example.elementsfoodapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private final LayoutInflater mInflater;
    private List<Food> mFoods; // Cached copy of foods

    FoodListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NotNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull FoodViewHolder holder, int position) {
        if (mFoods != null) {
            Food current = mFoods.get(position);
            holder.foodItemView.setText(current.getFood());
        } else {
            // Covers the case of data not being ready yet.
            holder.foodItemView.setText("Keine Lebensmittel");
        }
    }

    void setFoods(List<Food> foods) {
        mFoods = foods;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFoods != null)
            return mFoods.size();
        else return 0;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        private final TextView foodItemView;

        private FoodViewHolder(View itemView) {
            super(itemView);
            foodItemView = itemView.findViewById(R.id.foodTextView);
        }
    }
}
