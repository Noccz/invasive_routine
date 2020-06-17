package com.noccz.invasive_routine.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

import static com.noccz.invasive_routine.Utils.getGsonParser;

public class RoutineViewAdapter extends RecyclerView.Adapter {
    private List<TaskItem> taskItems;
    private RoutineView routineView;

    public RoutineViewAdapter(List<TaskItem> taskItems, RoutineView view) {
        this.taskItems = new ArrayList<>(taskItems);
        this.routineView = view;
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
        RoutineItemHolder routineItemHolder = (RoutineItemHolder) holder;
        final int pos = position;
        routineItemHolder.bind(taskItems.get(position));
        routineItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("taskItem", getGsonParser().toJson(taskItems.get(pos)));

                NavHostFragment.findNavController(routineView)
                        .navigate(R.id.action_RoutineView_to_TaskManager, bundle);
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }
}
