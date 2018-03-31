package chubbs.mymenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskActivity extends MainActivity {

    private static final String TAG = "TaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //since we're inheriting main activity. just call the super method to create our toolbar
        super.createToolbar();

        FloatingActionButton addCourse = (FloatingActionButton) findViewById(R.id.addcourseButton);
        addCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                drawAddTaskPopUp();
            }
        });
        
        FloatingActionButton addExtracurricular = (FloatingActionButton) findViewById(R.id.addExtracurricularButton);
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
        final EditText etPriority = alertLayout.findViewById(R.id.taskPriority);
        final TimePicker datePicker = alertLayout.findViewById(R.id.timePicker);;


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

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etUsername.getText().toString();
                String priority = etPriority.getText().toString();
                int taskHour = datePicker.getHour();
                int taskMinute = datePicker.getMinute();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }



}





