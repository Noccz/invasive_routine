package com.noccz.invasive_routine.routine;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.database.DatabaseManager;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

import static com.noccz.invasive_routine.Utils.findViewById;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_CONTENT_COL_INDEX;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_ID_COL_INDEX;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_IS_COMPLETED_COL_INDEX;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_TIME_COL_INDEX;

public class RoutineView extends Fragment {
    protected List<TaskItem> taskItems;
    protected RecyclerView recyclerView;
    protected RoutineViewAdapter routineViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = findViewById(container, R.id.add_button);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RoutineView.this)
                        .navigate(R.id.action_RoutineView_to_TaskManager);
                view.setVisibility(View.INVISIBLE);
            }
        });

        return inflater.inflate(R.layout.routine_view_content, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskItems = new ArrayList<>();
        recyclerView = findViewById(view, R.id.routine_view_content);
        routineViewAdapter = new RoutineViewAdapter(taskItems, this);
        fetchDatabaseItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getRootView().getContext()));
        recyclerView.setAdapter(routineViewAdapter);
    }

    private void fetchDatabaseItems() {
        DatabaseManager dbManager = new DatabaseManager(this.getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        if (cursor.moveToFirst()) {
            do {
                routineViewAdapter.addItem(TaskItem.newBuilder()
                        .withId(cursor.getInt(TASK_ID_COL_INDEX))
                        .withContent(cursor.getString(TASK_CONTENT_COL_INDEX))
                        .withTime(cursor.getString(TASK_TIME_COL_INDEX))
                        .withIsCompleted(cursor.getInt(TASK_IS_COMPLETED_COL_INDEX))
                        .build());
            } while (cursor.moveToNext());
        }
        dbManager.close();
    }
}
