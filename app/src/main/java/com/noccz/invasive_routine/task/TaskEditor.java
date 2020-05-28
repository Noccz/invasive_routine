package com.noccz.invasive_routine.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.noccz.invasive_routine.MainActivity;
import com.noccz.invasive_routine.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class TaskEditor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TimePicker timePicker = view.findViewById(R.id.task_time_picker);
        timePicker.setIs24HourView(true);
        setupToolbar(view);
        setupButtons(view);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(taskButtonOnClickListener());
    }

    private void setupButtons(View view) {
        Button createButton = view.getRootView().findViewById(R.id.create_task_button);
        createButton.setOnClickListener(taskButtonOnClickListener());

        Button cancelButton = view.getRootView().findViewById(R.id.cancel_task_button);
        cancelButton.setOnClickListener(taskButtonOnClickListener());

    }

    private View.OnClickListener taskButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.create_task_button) {
                    // Trigger SQL request to create a new TaskItem entity
                }
                NavHostFragment.findNavController(TaskEditor.this)
                        .popBackStack();
                Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
                toolbar.setNavigationIcon(null);
                ((MainActivity) getActivity()).setSupportActionBar(toolbar);
            }
        };
    }
}
