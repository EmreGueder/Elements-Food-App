package com.example.elementsfoodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

public class AddNewFoodActivity extends AppCompatActivity
        implements CustomAdapter.ListItemClickListener {

    private RecyclerView recyclerView;
    private String[] foodType;
    private String[] foodElements;
    private String[] foodFlavor;
    private String[] foodTempBehavior;
    private String[] foodTargetOrgan;

    private final boolean[] checkedFoodType = new boolean[14];
    private final boolean[] checkedFoodElements = new boolean[5];
    private final boolean[] checkedFoodFlavor = new boolean[5];
    private final boolean[] checkedFoodTempBehavior = new boolean[5];
    private final boolean[] checkedFoodTargetOrgan = new boolean[11];


    //TextInputEditText elementsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);

        CustomAdapter adapter = new CustomAdapter(this, this);
        recyclerView = findViewById(R.id.foodPropertiesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(adapter);

        Resources res = getResources();
        foodType = res.getStringArray(R.array.food_type_array);
        foodElements = res.getStringArray(R.array.food_elements_array);
        foodFlavor = res.getStringArray(R.array.food_flavor_array);
        foodTempBehavior = res.getStringArray(R.array.food_temp_behavior_array);
        foodTargetOrgan = res.getStringArray(R.array.food_target_organ);
        //elementsEditText = (TextInputEditText) findViewById(R.id.selectFoodElement);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_new_food_options_menu, menu);
        return true;
    }

    @Override
    public void onListItemClick(View v, int position) {
        openDialog(position);
    }

    public void openDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Building the list to be shown in AlertDialog
        if (position == 0) {
            //Setting AlertDialog Characteristics
            builder.setTitle("Wähle Lebensmittelart aus");

            builder.setMultiChoiceItems(
                    foodType, checkedFoodType, (dialog, which, isChecked) -> {
                        //Update the current item's checked status
                        checkedFoodType[which] = isChecked;

                        if (((AlertDialog) dialog).getListView().getCheckedItemCount() > 1) {
                            Toast.makeText(getApplicationContext(),
                                    "Maximal eine Art", Toast.LENGTH_SHORT).show();
                            checkedFoodType[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(
                                    which, false);
                        }
                    });

            //Set positive button
            builder.setPositiveButton("OK", (dialog, which) -> {
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) recyclerView
                        .findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i < checkedFoodType.length; i++) {
                    boolean checked = checkedFoodType[i];
                    if (checked) {
                        holder.getSecondaryTextView().setText(foodType[i]);
                    }
                }

                boolean allFalse = true;
                for (boolean b : checkedFoodType) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            });
        }
        else if (position == 1) {
            //Setting AlertDialog Characteristics
            builder.setTitle("Wähle Element(e) aus");

            builder.setMultiChoiceItems(
                    foodElements, checkedFoodElements, (dialog, which, isChecked) -> {
                        //Update the current item's checked status
                        checkedFoodElements[which] = isChecked;

                        if (((AlertDialog) dialog).getListView().getCheckedItemCount() > 2) {
                            Toast.makeText(getApplicationContext(),
                                    "Maximal 2 Elemente", Toast.LENGTH_SHORT).show();
                            checkedFoodElements[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(
                                    which, false);
                        }
                    });

            //Set positive button
            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder items = new StringBuilder();
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) recyclerView
                        .findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i < checkedFoodElements.length; i++) {
                    boolean checked = checkedFoodElements[i];
                    if (checked) {
                        items.append(foodElements[i]).append(", ");
                    }
                }
                // Delete last comma
                if (items.length() > 2)
                    items.deleteCharAt(items.length() - 2);
                holder.getSecondaryTextView().setText(items.toString());

                boolean allFalse = true;
                for (boolean b : checkedFoodElements) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            });
        }
        else if (position == 2) {
            //Setting AlertDialog Characteristics
            builder.setTitle("Wähle Geschmacksrichtung(en) aus");

            builder.setMultiChoiceItems(
                    foodFlavor, checkedFoodFlavor, (dialog, which, isChecked) -> {
                        //Update the current item's checked status
                        checkedFoodFlavor[which] = isChecked;

                        if (((AlertDialog) dialog).getListView().getCheckedItemCount() > 2) {
                            Toast.makeText(getApplicationContext(),
                                    "Maximal 2 Geschmacksrichtungen", Toast.LENGTH_SHORT)
                                    .show();
                            checkedFoodFlavor[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(
                                    which, false);
                        }
                    });

            //Set positive button
            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder items = new StringBuilder();
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) recyclerView
                        .findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i < checkedFoodFlavor.length; i++) {
                    boolean checked = checkedFoodFlavor[i];
                    if (checked) {
                        items.append(foodFlavor[i]).append(", ");
                    }
                }
                // Delete last comma
                if (items.length() > 2)
                    items.deleteCharAt(items.length() - 2);
                holder.getSecondaryTextView().setText(items.toString());

                boolean allFalse = true;
                for (boolean b : checkedFoodFlavor) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            });
        }
        else if (position == 3) {
            //Setting AlertDialog Characteristics
            builder.setTitle("Wähle thermische Wirkung(en) aus");

            builder.setMultiChoiceItems(
                    foodTempBehavior, checkedFoodTempBehavior, (dialog, which, isChecked) -> {
                        //Update the current item's checked status
                        checkedFoodTempBehavior[which] = isChecked;

                        if (((AlertDialog) dialog).getListView().getCheckedItemCount() > 2) {
                            Toast.makeText(getApplicationContext(),
                                    "Maximal 2 thermische Wirkungen", Toast.LENGTH_SHORT)
                                    .show();
                            checkedFoodTempBehavior[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(
                                    which, false);
                        }
                    });

            //Set positive button
            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder items = new StringBuilder();
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) recyclerView
                        .findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i < checkedFoodTempBehavior.length; i++) {
                    boolean checked = checkedFoodTempBehavior[i];
                    if (checked) {
                        items.append(foodTempBehavior[i]).append(", ");
                    }
                }

                // Delete last comma
                if (items.length() > 2)
                    items.deleteCharAt(items.length() - 2);
                holder.getSecondaryTextView().setText(items.toString());

                boolean allFalse = true;
                for (boolean b : checkedFoodTempBehavior) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            });
        }
        else if (position == 4) {
            //Setting AlertDialog Characteristics
            builder.setTitle("Wähle Zielorgan(e) aus");

            builder.setMultiChoiceItems(
                    foodTargetOrgan, checkedFoodTargetOrgan, (dialog, which, isChecked) -> {
                        //Update the current item's checked status
                        checkedFoodTargetOrgan[which] = isChecked;
                    });

            //Set positive button
            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder items = new StringBuilder();
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) recyclerView
                        .findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i < checkedFoodTargetOrgan.length; i++) {
                    boolean checked = checkedFoodTargetOrgan[i];
                    if (checked) {
                        items.append(foodTargetOrgan[i]).append(", ");
                    }
                }

                // Delete last comma
                if (items.length() > 2)
                    items.deleteCharAt(items.length() - 2);
                holder.getSecondaryTextView().setText(items.toString());

                boolean allFalse = true;
                for (boolean b : checkedFoodTargetOrgan) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            });
        }

        //Set negative button
        builder.setNegativeButton("Abbrechen", (dialog, which) ->
                Toast.makeText(getApplicationContext(), "Abgebrochen", Toast.LENGTH_SHORT)
                        .show());

        //Creating AlertDialog
        AlertDialog alertDialog = builder.create();
        //Displaying AlertDialog
        alertDialog.show();
    }
}
