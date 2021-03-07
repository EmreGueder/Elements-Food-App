package com.example.elementsfoodapp.ui.addnewfood;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elementsfoodapp.R;

/**This class binds the app data to the views (UI).*/
public class AddFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ListItemClickListener mOnClickListener;
    private final String[] foodProperties;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;
        private final TextView secondaryTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            titleTextView = view.findViewById(R.id.mtrl_list_item_title_text);
            secondaryTextView = view.findViewById(R.id.mtrl_list_item_secondary_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(v, position);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getSecondaryTextView() {
            return secondaryTextView;
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(View v, int position);
    }

    public AddFoodAdapter(Context context, ListItemClickListener onClickListener) {
        Resources res = context.getResources();
        foodProperties = res.getStringArray(R.array.food_properties_array);
        mOnClickListener = onClickListener;
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
        ((ViewHolder)holder).getTitleTextView().setText(foodProperties[position]);
    }

    @Override
    public int getItemCount() {
        return foodProperties.length;
    }
}