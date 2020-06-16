package com.noccz.invasive_routine.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.noccz.invasive_routine.MainActivity;
import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.database.DatabaseManager;

import static com.noccz.invasive_routine.Utils.findViewById;

public class TaskEditor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TimePicker timePicker = findViewById(view, R.id.task_time_picker);
        timePicker.setIs24HourView(true);
        setupToolbar(view);
        setupButtons(view);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = findViewById(view, R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(taskButtonOnClickListener());
    }

    private void setupButtons(View view) {
        Button createButton = findViewById(view, R.id.create_task_button);
        createButton.setOnClickListener(taskButtonOnClickListener());

        Button cancelButton = findViewById(view, R.id.cancel_task_button);
        cancelButton.setOnClickListener(taskButtonOnClickListener());

    }

    private View.OnClickListener taskButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.create_task_button) {
                    addTaskItem(view);
                }
                NavHostFragment.findNavController(TaskEditor.this)
                        .popBackStack();
                Toolbar toolbar = findViewById(view, R.id.toolbar);
                toolbar.setNavigationIcon(null);
                ((MainActivity) getActivity()).setSupportActionBar(toolbar);
            }
        };
    }

    private void addTaskItem(View view) {
        EditText content = findViewById(view, R.id.task_description_editor);
        TimePicker time = findViewById(view, R.id.task_time_picker);
        DatabaseManager dbManager = new DatabaseManager(this.getContext());
        dbManager.open();
        dbManager.insert(TaskItem.newBuilder()
                .withContent(content.getText().toString())
                .withTime(getSelectedTime(time))
                .withIsCompleted(0)
                .build());
        dbManager.close();
    }

    private String getSelectedTime(TimePicker time) {
        int rawHour = time.getHour();
        String hour = rawHour < 10 ? "0" + rawHour : Integer.toString(rawHour);
        int rawMinute = time.getMinute();
        String minute = rawMinute < 10 ? "0" + rawMinute : Integer.toString(rawMinute);
        return hour + ":" + minute;
    }
}
