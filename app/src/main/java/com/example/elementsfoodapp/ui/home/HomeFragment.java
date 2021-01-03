package com.example.elementsfoodapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elementsfoodapp.ui.addnewfood.AddFoodActivity;
import com.example.elementsfoodapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddFoodActivity.class);
            v.getContext().startActivity(intent);
        });
        return root;
    }
}