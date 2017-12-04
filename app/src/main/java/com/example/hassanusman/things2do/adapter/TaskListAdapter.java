package com.example.hassanusman.things2do.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hassanusman.things2do.R;
import com.example.hassanusman.things2do.presistence.model.Task;
import com.example.hassanusman.things2do.ui.home.TaskViewModel;
import com.example.hassanusman.things2do.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HassanUsman on 4/12/17.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private List<Task> taskList=new ArrayList<>();

    TaskViewModel taskViewModel=null;

    public TaskListAdapter(TaskViewModel taskViewModel){
        this.taskViewModel=taskViewModel;
        taskViewModel.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items ->{
                    Collections.sort(items);
                    this.taskList=items;
                            Log.e("Adapter",""+items.size());
                    notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.e("Adapter",""+throwable.getMessage());
                });
    }
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view,parent,false);
        return new TaskHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        if(taskList.get(position).getPending()==1) {
            holder.checked.setVisibility(View.GONE);
            holder.pending.setVisibility(View.VISIBLE);
        }
        else{
            holder.checked.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
        }
        holder.taskName.setText(taskList.get(position).getName()+"");
        holder.taskView.setOnClickListener((view)->{
            if(taskList.get(position).getPending()==Constants.TASK_DONE)
                taskList.get(position).setPending(Constants.TASK_PENDING);
            else
                taskList.get(position).setPending(Constants.TASK_DONE);

            taskViewModel.update(taskList.get(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{
                        notifyDataSetChanged();
                    });

        });
        holder.taskView.setOnLongClickListener((view)->{
            taskViewModel.delete(taskList.get(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{
                        notifyDataSetChanged();
                    });

            return true;
        });


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        TextView taskName;
        CardView taskView;
        ImageView pending, checked;
        public TaskHolder(View v){
            super(v);
            taskName=v.findViewById(R.id.task_name);
            pending=v.findViewById(R.id.pending);
            checked=v.findViewById(R.id.checked);
            taskView=v.findViewById(R.id.task);
        }

    }
}
