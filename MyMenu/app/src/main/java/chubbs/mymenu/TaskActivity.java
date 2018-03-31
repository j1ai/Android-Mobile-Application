package chubbs.mymenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

import chubbs.mymenu.models.Task;

public class TaskActivity extends MainActivity{

    private static final String TAG = "TaskActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        //db.getAllTasks();

        FloatingActionButton addCourse = (FloatingActionButton) findViewById(R.id.addcourseButton);
        addCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                drawAddTaskPopUp();
            }
        });
    }

    public void drawAddTaskPopUp(){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_task_alert, null);
        final EditText etUsername = alertLayout.findViewById(R.id.taskName);
        final Spinner etPriority = alertLayout.findViewById(R.id.prioritySpinner);
        final EditText etDuration = alertLayout.findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etPriority.setAdapter(adapter);
        final TimePicker timePicker = alertLayout.findViewById(R.id.timePicker);
        final DatePicker datePicker = alertLayout.findViewById(R.id.datePicker);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog.Builder done = alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskName = etUsername.getText().toString();
                String priority = etPriority.getSelectedItem().toString();
                int duration = Integer.parseInt(etDuration.getText().toString());
                int taskHour = timePicker.getCurrentHour();
                int taskMinute = timePicker.getCurrentMinute();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String min = String.valueOf(taskMinute);
                String hour = String.valueOf(taskHour);
                String start_time = hour + ":" + min;
                String start_date = day + "/" + month + "/" + year;
                Task newTask = new Task(taskName,priority,start_date,start_time,duration);
                db.addTask(newTask);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }



}




