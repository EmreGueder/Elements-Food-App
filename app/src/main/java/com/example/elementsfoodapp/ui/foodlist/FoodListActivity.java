package com.example.elementsfoodapp.ui.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ActionMode;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elementsfoodapp.R;
import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.ui.addnewfood.AddEditFoodActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;


public class FoodListActivity extends AppCompatActivity {

    public static final int ADD_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;

    private FoodListAdapter mAdapter;
    private FloatingActionButton mFab;
    private FoodViewModel mFoodViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private BottomSheetBehavior<LinearLayout> mBottomSheetBehavior;
    private Food mCurrentFood;
    private ActionMode mActionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        getAllFoods();

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(FoodListActivity.this,
                    AddEditFoodActivity.class);
            startActivityForResult(intent, ADD_FOOD_ACTIVITY_REQUEST_CODE);
        });

        mRecyclerView = findViewById(R.id.foodListRecyclerView);
        mAdapter = new FoodListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
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
                mFoodViewModel.insertLastViewedFood(food.getId());
                startActivityForResult(intent, EDIT_FOOD_ACTIVITY_REQUEST_CODE);
            }
        });

        mAdapter.setOnItemLongClickListener(new FoodListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Food food) {
                if (mActionMode == null) {
                    mCurrentFood = food;
                    mActionMode = startActionMode(actionModeCallback);
                }
            }
        });

        ImageView hideArrow = findViewById(R.id.bottomSheetHideArrow);
        LinearLayout bottomSheet = findViewById(R.id.bottomSheetFilter);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().show();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mFab.setVisibility(View.VISIBLE);
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
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));

        Button buttonShowResults = findViewById(R.id.buttonShowResults);
        ChipGroup typeChipGroup = findViewById(R.id.type_chipgroup);
        ChipGroup elementChipGroup = findViewById(R.id.element_chipgroup);
        ChipGroup flavorChipGroup = findViewById(R.id.flavor_chipgroup);
        ChipGroup thermalEffectChipGroup = findViewById(R.id.thermaleffect_chipgroup);
        ChipGroup targetOrganChipGroup = findViewById(R.id.targetorgan_chipgroup);

        typeChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                getAllFoods();
            }
            else {
                elementChipGroup.clearCheck();
                flavorChipGroup.clearCheck();
                thermalEffectChipGroup.clearCheck();
                targetOrganChipGroup.clearCheck();
                Chip chip = group.findViewById(checkedId);
                String property = chip.getText().toString();
                getTypeFilterResultFromDb(property);
            }
        });

        elementChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                getAllFoods();
            }
            else {
                typeChipGroup.clearCheck();
                flavorChipGroup.clearCheck();
                thermalEffectChipGroup.clearCheck();
                targetOrganChipGroup.clearCheck();
                Chip chip = group.findViewById(checkedId);
                String property = chip.getText().toString();
                getElementFilterResultFromDb(property);
            }
        });

        flavorChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                getAllFoods();
            }
            else {
                elementChipGroup.clearCheck();
                typeChipGroup.clearCheck();
                thermalEffectChipGroup.clearCheck();
                targetOrganChipGroup.clearCheck();
                Chip chip = group.findViewById(checkedId);
                String property = "%" + chip.getText().toString() + "%";
                getFlavorFilterResultFromDb(property);
            }
        });

        thermalEffectChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                getAllFoods();
            }
            else {
                typeChipGroup.clearCheck();
                elementChipGroup.clearCheck();
                flavorChipGroup.clearCheck();
                targetOrganChipGroup.clearCheck();
                Chip chip = group.findViewById(checkedId);
                String property = "%" + chip.getText().toString() + "%";
                getThermalEffectFilterResultFromDb(property);
            }
        });

        targetOrganChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {
                getAllFoods();
            }
            else {
                typeChipGroup.clearCheck();
                elementChipGroup.clearCheck();
                flavorChipGroup.clearCheck();
                thermalEffectChipGroup.clearCheck();
                Chip chip = group.findViewById(checkedId);
                String property = "%" + chip.getText().toString() + "%";
                getTargetOrganFilterResultFromDb(property);
            }
        });

        buttonShowResults.setOnClickListener(v ->
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_list_menu, menu);

        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    getFoodFromDb(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    getFoodFromDb(newText);
                }
                return true;
            }
        });
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();
        //Required for SearchView to gain focus,
        //resulting the keyboard to show up after tapping the search icon
        if (id == R.id.search) {
            mSearchView.setIconified(false);
            return true;
        } else if (id == R.id.filter) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mFab.setVisibility(View.INVISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            mode.setTitle("Optionen");
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Food localFood = mCurrentFood;
            if (item.getItemId() == R.id.action_delete && localFood != null) {
                Toast.makeText(FoodListActivity.this, "Lösche " +
                        localFood.getFood(), Toast.LENGTH_SHORT).show();
                // Delete the food
                mCurrentFood = null;
                mFoodViewModel.deleteFood(localFood);
                mode.finish();
            }
            if (item.getItemId() == R.id.action_favorite && localFood != null) {
                Toast.makeText(FoodListActivity.this, localFood.getFood() +
                        " als Favorit hinzugefügt", Toast.LENGTH_SHORT).show();
                //Add to favorites list
                mCurrentFood = null;
                mFoodViewModel.insertFavoriteFood(localFood.getId());
                mode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.setSelectedPos(RecyclerView.NO_POSITION);
            mActionMode = null;
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Extract StringArray Data and insert into Database
            String[] foodData = data.getStringArrayExtra(AddEditFoodActivity.EXTRA_REPLY);
            Food food = new Food(foodData[0], foodData[1], foodData[2], foodData[3],
                    foodData[4], foodData[5], foodData[6]);
            mFoodViewModel.insert(food);
            Toast.makeText(this, food.getFood() + " hinzugefügt", Toast.LENGTH_SHORT)
                    .show();
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

    public void getFoodFromDb(String searchText) {
        String mSearchText = "%" + searchText + "%";
        mFoodViewModel.getSearchResults(mSearchText).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getAllFoods() {
        mFoodViewModel.getAllFoods().observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getTypeFilterResultFromDb(String property) {
        mFoodViewModel.getTypeFilterResults(property).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getElementFilterResultFromDb(String property) {
        mFoodViewModel.getElementFilterResults(property).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getFlavorFilterResultFromDb(String property) {
        mFoodViewModel.getFlavorFilterResults(property).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getThermalEffectFilterResultFromDb(String property) {
        mFoodViewModel.getThermalEffectFilterResults(property).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }

    public void getTargetOrganFilterResultFromDb(String property) {
        mFoodViewModel.getTargetOrganFilterResults(property).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }
}