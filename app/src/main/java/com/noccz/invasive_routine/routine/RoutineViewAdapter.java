package com.noccz.invasive_routine.routine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineViewAdapter extends RecyclerView.Adapter {
    private List<TaskItem> taskItems;

    public RoutineViewAdapter(List<TaskItem> taskItems) {
        this.taskItems = new ArrayList<>(taskItems);
    }

    public void addItem(TaskItem item) {
        taskItems.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new RoutineItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RoutineItemHolder) holder).bind(taskItems.get(position));
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }
}
