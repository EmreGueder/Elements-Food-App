package com.example.elementsfoodapp.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elementsfoodapp.R;
import com.example.elementsfoodapp.db.Food;
import com.example.elementsfoodapp.ui.addnewfood.AddEditFoodActivity;

import static android.app.Activity.RESULT_OK;

/**Fragment to display the favorites list.*/
public class FavoritesListFragment extends Fragment {

    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;

    private FavoritesViewModel mFavoritesViewModel;
    private FavoritesListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Food mCurrentFood;
    private ActionMode mActionMode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFavoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        getFavoriteFoods();

        mRecyclerView = root.findViewById(R.id.foodListRecyclerView);
        mAdapter = new FavoritesListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new FavoritesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent intent = new Intent(getActivity(), AddEditFoodActivity.class);
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

        mAdapter.setOnItemLongClickListener(new FavoritesListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Food food) {
                if (mActionMode == null) {
                    mCurrentFood = food;
                    mActionMode = requireActivity().startActionMode(actionModeCallback);
                }
            }
        });
        return root;
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
            menu.findItem(R.id.action_favorite).setVisible(false);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Food localFood = mCurrentFood;
            if (item.getItemId() == R.id.action_delete && localFood != null) {
                Toast.makeText(getContext(), "Lösche " +
                        localFood.getFood(), Toast.LENGTH_SHORT).show();
                // Delete the food
                mCurrentFood = null;
                mFavoritesViewModel.deleteFavoriteFood(localFood.getId());
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


        if (requestCode == EDIT_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditFoodActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Nahrungsmittel kann nicht aktualisiert werden",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // Extract StringArray Data and update it in the Database
            String[] foodData = data.getStringArrayExtra(AddEditFoodActivity.EXTRA_REPLY);
            Food food = new Food(foodData[0], foodData[1], foodData[2], foodData[3],
                    foodData[4], foodData[5], foodData[6]);
            food.setId(id);
            mFavoritesViewModel.update(food);
            mFavoritesViewModel.insertFavoriteFood(food.getId());
            Toast.makeText(getContext(), "Aktualisiert", Toast.LENGTH_SHORT).show();
        }
    }

    public void getFavoriteFoods() {
        mFavoritesViewModel.getFavoriteFoods().observe(getViewLifecycleOwner(), foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }
}