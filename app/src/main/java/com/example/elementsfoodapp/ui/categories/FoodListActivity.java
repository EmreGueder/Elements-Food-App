package com.example.elementsfoodapp.ui.categories;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.elementsfoodapp.Food;
import com.example.elementsfoodapp.R;
import com.example.elementsfoodapp.ui.addnewfood.AddFoodActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    public static final int ADD_FOOD_ACTIVITY_REQUEST_CODE = 1;

    private FoodViewModel mFoodViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerView = findViewById(R.id.foodListRecyclerView);
        final FoodListAdapter adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFoods().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                // Update the cached copy of the words in the adapter.
                adapter.setFoods(foods);
            }
        });

        ImageView hideArrow = findViewById(R.id.bottomSheetHideArrow);
        LinearLayout bottomSheet = findViewById(R.id.bottomSheetFilter);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().show();
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        hideArrow.setOnClickListener(v ->
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categories_menu, menu);

        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();
        //Required for SearchView to gain focus,
        //resulting the keyboard to show up after tapping the search icon
        if (id == R.id.search) {
            searchView.setIconified(false);
        }
        else if (id == R.id.filter) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] foodData = data.getStringArrayExtra(AddFoodActivity.EXTRA_REPLY);
            Food food = new Food(foodData[0], foodData[1], foodData[2], foodData[3],
                                 foodData[4], foodData[5], foodData[6]);
            mFoodViewModel.insert(food); //parentactivity is foodlistactivity instead of mainactivity
        }
    }
}