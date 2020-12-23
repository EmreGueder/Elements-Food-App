package com.example.elementsfoodapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String[] foodProperties;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.mtrl_list_item_text);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public CustomAdapter(Context context) {
        Resources res = context.getResources();
        foodProperties = res.getStringArray(R.array.food_properties_array);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).getTextView().setText(foodProperties[position]);
    }

    @Override
    public int getItemCount() {
        return foodProperties.length;
    }
}
