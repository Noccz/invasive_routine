package com.noccz.invasive_routine.routine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.task.TaskItem;

public class RoutineItemHolder extends RecyclerView.ViewHolder {
    private TextView taskTime, taskContent;

    public RoutineItemHolder(@NonNull View itemView) {
        super(itemView);
        taskTime = itemView.findViewById(R.id.task_time);
        taskContent = itemView.findViewById(R.id.task_content);
    }

    void bind(TaskItem item) {
        taskTime.setText(item.getTime());
        taskContent.setText(item.getContent());
    }
}
