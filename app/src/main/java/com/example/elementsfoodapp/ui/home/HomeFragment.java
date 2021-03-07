package com.example.elementsfoodapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.elementsfoodapp.ui.favorites.FavoritesListAdapter;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;

/**Fragment displaying the home entry point which also shows the last viewed items.*/
public class HomeFragment extends Fragment {

    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;

    private HomeViewModel mHomeViewModel;
    private HomeListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mHomeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        getLastViewedFoods();


        mRecyclerView = root.findViewById(R.id.foodListRecyclerView);
        mAdapter = new HomeListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new HomeListAdapter.OnItemClickListener() {
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
                mHomeViewModel.insertLastViewedFood(food.getId());
                startActivityForResult(intent, EDIT_FOOD_ACTIVITY_REQUEST_CODE);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_history) {
            mHomeViewModel.deleteLastViewedFoods();
        }
        return true;
    }

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
            mHomeViewModel.update(food);
            mHomeViewModel.insertLastViewedFood(food.getId());
            Toast.makeText(getContext(), "Aktualisiert", Toast.LENGTH_SHORT).show();
        }
    }

    public void getLastViewedFoods() {
        mHomeViewModel.getLastViewedFoods().observe(getViewLifecycleOwner(), foods -> {
            // Update the cached copy of the words in the adapter.
            mAdapter.setFoods(foods);
        });
    }
}