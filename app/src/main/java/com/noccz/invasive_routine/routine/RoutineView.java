package com.noccz.invasive_routine.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineView extends Fragment {
    protected List<TaskItem> taskItems;
    protected RecyclerView taskRecycler;
    protected RoutineViewAdapter routineViewAdapter;

    // TODO: Implement a database query to read all existing TaskItem entities and fill up the list on creation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = container.getRootView().findViewById(R.id.add_button);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RoutineView.this)
                        .navigate(R.id.action_RoutineView_to_TaskManager);
                view.setVisibility(View.INVISIBLE);
            }
        });


        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskItems = new ArrayList<>();
        taskRecycler = view.getRootView().findViewById(R.id.recyclerView);
        routineViewAdapter = new RoutineViewAdapter(taskItems);
        taskRecycler.setLayoutManager(new LinearLayoutManager(view.getRootView().getContext()));
        taskRecycler.setAdapter(routineViewAdapter);
    }
}
