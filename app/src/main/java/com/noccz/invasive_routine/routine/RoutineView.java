package com.noccz.invasive_routine.routine;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.database.DatabaseManager;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_CONTENT;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_TIME;

public class RoutineView extends Fragment {
    protected List<TaskItem> taskItems;
    protected RecyclerView taskRecycler;
    protected RoutineViewAdapter routineViewAdapter;

    final String[] from = new String[] { TASK_CONTENT, TASK_TIME};
    final int[] to = new int[] { R.id.task_content, R.id.task_time };

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

        DatabaseManager dbManager = new DatabaseManager(this.getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getContext(), R.layout.content_main, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        View taskRecyclerView = container.getRootView().findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setAdapter(adapter);

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskItems = new ArrayList<>();
        taskRecycler = view.getRootView().findViewById(R.id.taskRecyclerView);
        routineViewAdapter = new RoutineViewAdapter(taskItems);
        taskRecycler.setLayoutManager(new LinearLayoutManager(view.getRootView().getContext()));
        taskRecycler.setAdapter(routineViewAdapter);
    }
}
