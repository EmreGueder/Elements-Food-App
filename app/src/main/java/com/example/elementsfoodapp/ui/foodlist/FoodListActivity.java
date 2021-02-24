package com.example.elementsfoodapp.ui.foodlist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.R;
import com.example.elementsfoodapp.ui.addnewfood.AddEditFoodActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    public static final int ADD_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;

    private FloatingActionButton fab;
    private FoodViewModel mFoodViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(FoodListActivity.this,
                    AddEditFoodActivity.class);
            startActivityForResult(intent, ADD_FOOD_ACTIVITY_REQUEST_CODE);
        });

        recyclerView = findViewById(R.id.foodListRecyclerView);
        final FoodListAdapter adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Food myFood = adapter.getFoodAtPosition(position);
                        Toast.makeText(FoodListActivity.this, "Lösche " +
                                myFood.getFood(), Toast.LENGTH_SHORT).show();

                        // Delete the food
                        mFoodViewModel.deleteFood(myFood);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent intent = new Intent(FoodListActivity.this,
                        AddEditFoodActivity.class);
                String[] foodData = new String[7];
                foodData[0] = food.getFood();
                foodData[1] = food.getEffect();
                foodData[2] = food.getType();
                foodData[3] = food.getElement();
                foodData[4] = food.getFlavor();
                foodData[5] = food.getThermalEffect();
                foodData[6] = food.getTargetOrgan();
                intent.putExtra(AddEditFoodActivity.EXTRA_ID, food.getId());
                intent.putExtra(AddEditFoodActivity.EXTRA_REPLY, foodData);
                startActivityForResult(intent, EDIT_FOOD_ACTIVITY_REQUEST_CODE);
            }
        });

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
                            fab.setVisibility(View.VISIBLE);
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
        inflater.inflate(R.menu.food_list_menu, menu);

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
            return true;
        } else if (id == R.id.filter) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            recyclerView.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.INVISIBLE);
            return true;
        } else if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mFoodViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Extract StringArray Data and insert into Database
            String[] foodData = data.getStringArrayExtra(AddEditFoodActivity.EXTRA_REPLY);
            Food food = new Food(foodData[0], foodData[1], foodData[2], foodData[3],
                    foodData[4], foodData[5], foodData[6]);
            mFoodViewModel.insert(food);
        } else if (requestCode == EDIT_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditFoodActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Nahrungsmittel kann nicht aktualisiert werden",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // Extract StringArray Data and update it in the Database
            String[] foodData = data.getStringArrayExtra(AddEditFoodActivity.EXTRA_REPLY);
            Food food = new Food(foodData[0], foodData[1], foodData[2], foodData[3],
                    foodData[4], foodData[5], foodData[6]);
            food.setId(id);
            mFoodViewModel.update(food);
            Toast.makeText(this, "Aktualisiert", Toast.LENGTH_SHORT).show();
        }
    }
}