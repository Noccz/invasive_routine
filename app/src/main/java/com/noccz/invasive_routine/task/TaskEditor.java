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
import static com.noccz.invasive_routine.Utils.getGsonParser;

public class TaskEditor extends Fragment {
    private TaskItem taskItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.task_editor_content, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TimePicker timePicker = findViewById(view, R.id.task_time_picker);
        timePicker.setIs24HourView(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            taskItem = getGsonParser().fromJson(bundle.getString("taskItem"), TaskItem.class);
            EditText description = findViewById(view, R.id.task_description_editor);
            description.setText(taskItem.getContent());
            timePicker.setHour(taskItem.getHour());
            timePicker.setMinute(taskItem.getMinute());
        }
        setupToolbar(view);
        setupButtons(view, bundle != null);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = findViewById(view, R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(taskButtonOnClickListener());
    }

    private void setupButtons(View view, boolean showUpdateButton) {
        Button actionButton = showUpdateButton
                ? (Button) findViewById(view, R.id.update_task_button)
                : (Button) findViewById(view, R.id.create_task_button);
        actionButton.setOnClickListener(taskButtonOnClickListener());
        actionButton.setVisibility(View.VISIBLE);

        Button cancelButton = findViewById(view, R.id.cancel_task_button);
        cancelButton.setOnClickListener(taskButtonOnClickListener());

    }

    private View.OnClickListener taskButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.create_task_button:
                        addTaskItem(view);
                        break;
                    case R.id.update_task_button:
                        updateTaskItem(view, taskItem);
                        break;
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

    private void updateTaskItem(View view, TaskItem item) { //TODO: Kolla om id är satt. Den behövs för att kunna uppdatera itemlist i routineview
        if(item != null) {
            EditText content = findViewById(view, R.id.task_description_editor);
            TimePicker time = findViewById(view, R.id.task_time_picker);
            DatabaseManager dbManager = new DatabaseManager(this.getContext());
            dbManager.open();
            dbManager.update(item.newBuilderWithCurrent()
                    .withContent(content.getText().toString())
                    .withTime(getSelectedTime(time))
                    .withIsCompleted(0)
                    .build());
            dbManager.close();
        }
    }

    private String getSelectedTime(TimePicker time) {
        int rawHour = time.getHour();
        String hour = rawHour < 10 ? "0" + rawHour : Integer.toString(rawHour);
        int rawMinute = time.getMinute();
        String minute = rawMinute < 10 ? "0" + rawMinute : Integer.toString(rawMinute);
        return hour + ":" + minute;
    }
}
