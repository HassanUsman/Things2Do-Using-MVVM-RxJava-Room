package com.example.hassanusman.things2do.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hassanusman.things2do.R;
import com.example.hassanusman.things2do.adapter.TaskListAdapter;
import com.example.hassanusman.things2do.presistence.model.Task;
import com.example.hassanusman.things2do.util.Constants;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView taskList;
    static final String TAG=Home.class.getSimpleName();

    @Inject
    TaskViewModel taskViewModel;
    View snackView;
    CoordinatorLayout rootLayout;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection
                .inject(this);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.rounded_back);
        setContentView(R.layout.activity_home);
        rootLayout=findViewById(R.id.home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskList=findViewById(R.id.task_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackView=view;
                createAddTaskDialog();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        taskList.setAdapter(new TaskListAdapter(taskViewModel));
    }

    // creating dialog to add Task

    void createAddTaskDialog(){
        Dialog dialog=new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_back);
        dialog.getWindow().setDimAmount(0.5f);
        dialog.setContentView(R.layout.add_task_dialog);
        EditText title=dialog.findViewById(R.id.task_title);
        EditText des=dialog.findViewById(R.id.des);
        FloatingActionButton add=dialog.findViewById(R.id.add);
        dialog.show();

        add.setOnClickListener((view)->{
            //Toast.makeText(getApplicationContext(),"hitting clck",Toast.LENGTH_SHORT).show();
            if(title.getText().toString().equals("")
                    || des.getText().toString().equals("") )
            {
                Snackbar.make(snackView,"Please fill the fields",Snackbar.LENGTH_SHORT).show();
                return;
            }
            compositeDisposable.add(taskViewModel
                    .insertTask(new Task(title.getText().toString()
                    , Constants.TASK_PENDING
                            ,des.getText().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{
                        Snackbar.make(snackView,"Task Added Successfully",Snackbar.LENGTH_SHORT).show();
                        dialog.dismiss();
                },throwable -> {
                        Log.e(TAG,"Error: 34 "+throwable.getMessage());
            }));
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        /*compositeDisposable.add(taskViewModel.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->{
                    taskList.setAdapter(new TaskListAdapter(taskViewModel));
                },throwable -> {}));*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

