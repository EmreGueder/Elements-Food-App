package com.example.elementsfoodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

public class AddNewFoodActivity extends AppCompatActivity
        implements CustomAdapter.ListItemClickListener {

    private RecyclerView recyclerView;
    private String[] elements;
    private final boolean[] checkedElements = new boolean[]{false, false, false, false, false};

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
        elements = res.getStringArray(R.array.elements_array);
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

        //Setting AlertDialog Characteristics
        builder.setTitle("Select items");

        //Building the list to be shown in AlertDialog
        if(position == 1) {
            builder.setMultiChoiceItems(
                    elements, checkedElements, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            //Update the current item's checked status
                            checkedElements[which] = isChecked;
                        }
                    });
        }

        //Set positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder items = new StringBuilder();
                CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder)
                        recyclerView.findViewHolderForAdapterPosition(position);
                assert holder != null;
                for (int i = 0; i<checkedElements.length; i++) {
                    boolean checked = checkedElements[i];
                    if (checked) {
                        items.append(elements[i]).append(", ");
                    }
                }

                if(items.length() > 2)
                    items.deleteCharAt(items.length() - 2);
                holder.getSecondaryTextView().setText(items.toString());

                boolean allFalse = true;
                for (boolean b : checkedElements) {
                    if (b) {
                        allFalse = false;
                        break;
                    }
                }
                if (allFalse) {
                    holder.getSecondaryTextView().setText("");
                }
            }
        });

        //Set negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        //Creating AlertDialog
        AlertDialog dialog = builder.create();
        //Displaying AlertDialog
        dialog.show();
    }
}
