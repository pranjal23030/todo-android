package com.example.namiminiproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namiminiproject.R;
import com.example.namiminiproject.activity.CreateTaskActivity;
import com.example.namiminiproject.activity.HomeActivity;
import com.example.namiminiproject.database.entities.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ItemViewHolder> {

    // Variables
    ArrayList<Task> taskArrayList = new ArrayList<>();
    Context context;

    public TaskAdapter(ArrayList<Task> mTaskArrayList, Context mContext) {
        this.taskArrayList = mTaskArrayList;
        this.context = mContext;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initializing layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Set values in view i.e setText
        Task task = taskArrayList.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.tvTaskDescription.setText(task.getDescription());
        holder.tvTaskStatus.setText(task.getStatus());
        holder.tvTaskDueDate.setText(task.getDueDate());
        holder.tvTaskDueTime.setText(task.getDueTime());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateTaskActivity.class);

                intent.putExtra("task_operation", "edit");
                intent.putExtra("task", task);

                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) context).showDeleteDialog(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // Working on findViewById
        TextView tvTaskTitle, tvTaskDescription, tvTaskStatus, tvTaskDueDate, tvTaskDueTime;
        ImageView editButton, deleteButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            initView();

        }

        void initView() {
            tvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            tvTaskDescription = itemView.findViewById(R.id.tv_task_description);
            tvTaskStatus = itemView.findViewById(R.id.tv_task_status);
            tvTaskDueTime = itemView.findViewById(R.id.tv_task_due_time);
            tvTaskDueDate = itemView.findViewById(R.id.tv_task_due_date);
            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}