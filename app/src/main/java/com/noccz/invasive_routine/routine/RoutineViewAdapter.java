package com.noccz.invasive_routine.routine;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.noccz.invasive_routine.R;
import com.noccz.invasive_routine.database.DatabaseManager;
import com.noccz.invasive_routine.task.TaskItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import static com.noccz.invasive_routine.Utils.findViewById;
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
        taskItems.sort(new Comparator<TaskItem>() {
            @Override
            public int compare(TaskItem item1, TaskItem item2) {
                if (item1.getHour() < item2.getHour()) {
                    return -1;
                } else if (item1.getHour() == item2.getHour()) {
                    return Integer.compare(item1.getMinute(), item2.getMinute());
                } else {
                    return 1;
                }
            }
        });
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
        final TaskItem taskItem = taskItems.get(position);
        routineItemHolder.bind(taskItems.get(position));
        if (taskItem.isCompleted()) {
            ((CardView)routineItemHolder.itemView).setCardBackgroundColor(routineView.getResources().getColor(R.color.colorComplete, null));
        } else {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.HOUR_OF_DAY) > taskItem.getHour() ||
                    calendar.get(Calendar.HOUR_OF_DAY) == taskItem.getHour() && calendar.get(Calendar.MINUTE) > taskItem.getMinute()) {
                ((CardView)routineItemHolder.itemView).setCardBackgroundColor(routineView.getResources().getColor(R.color.colorNotComplete, null));
            }
        }
        routineItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("taskItem", getGsonParser().toJson(taskItem));

                NavHostFragment.findNavController(routineView)
                        .navigate(R.id.action_RoutineView_to_TaskManager, bundle);
                findViewById(view.getRootView(), R.id.add_button).setVisibility(View.INVISIBLE);
            }
        });
        routineItemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                PopupMenu popup = new PopupMenu(view.getRootView().getContext(),view, Gravity.END, R.attr.actionOverflowMenuStyle, 0);
                popup.inflate(R.menu.menu_task_item);
                if (taskItem.isCompleted()) {
                    popup.getMenu().getItem(0).setTitle(R.string.undone);
                } else {
                    popup.getMenu().getItem(0).setTitle(R.string.done);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                deleteTaskItem(taskItem);
                                return true;
                            case R.id.action_task_status:
                                updateTaskStatus(taskItem);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    private void deleteTaskItem(TaskItem item) {
        DatabaseManager dbManager = new DatabaseManager(routineView.getContext());
        dbManager.open();
        dbManager.delete(item.getId());
        dbManager.close();
        notifyDataSetChanged();
        refreshView();
    }

    private void updateTaskStatus(TaskItem taskItem) {
        DatabaseManager dbManager = new DatabaseManager(routineView.getContext());
        dbManager.open();
        if (!taskItem.isCompleted()) {
            dbManager.update(taskItem.newBuilderWithCurrent()
                    .withIsCompleted(1)
                    .build());
        } else {
            dbManager.update(taskItem.newBuilderWithCurrent()
                    .withIsCompleted(0)
                    .build());
        }
        dbManager.close();
        refreshView();
    }

    private void refreshView() {
        notifyDataSetChanged();
        routineView.getParentFragmentManager().beginTransaction()
                .detach(routineView)
                .attach(routineView)
                .commit();
    }
}
