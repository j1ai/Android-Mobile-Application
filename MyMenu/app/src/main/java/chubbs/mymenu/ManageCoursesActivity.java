package chubbs.mymenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chubbs.mymenu.DataAccess.ManageData;
import chubbs.mymenu.models.Course;

public class ManageCoursesActivity extends MainActivity {
    String courseName;
    ListView listView;
    ArrayAdapter adapter;
    List<Course> courseids = new ArrayList<Course>();
    List<String> courses = new ArrayList<String>();
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db.getAllCourses();
        courseids = db.getAll_course();

        if (courseids.isEmpty()) {
            Log.d("NullTest", "Courses is null :(");
        }

        for (Course course : courseids) {
            courses.add(course.getCid());
            Log.d("CourseTest", course.getCid());
        }
        setContentView(R.layout.activity_manage_courses);

        //since we're inheriting main activity. just call the super method to create our toolbar
        super.createToolbar();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                courses);

        listView = findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courseName = (String) listView.getItemAtPosition(position);
                editCoursePopUp();
            }
        });
    }

    public void editCoursePopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Course");

        final EditText inputText = new EditText(this);
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        //inputText.setText(courseName);
        inputText.setHint("Rename Course");
        builder.setView(inputText);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                courseName = inputText.getText().toString();
                Toast.makeText(getBaseContext(), courseName, Toast.LENGTH_LONG).show();
            }
        });

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteCoursePopUp();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteCoursePopUp() {
        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
        deleteBuilder.setTitle("Confirm");
        deleteBuilder.setMessage("Are you sure you want to remove this course?");

        deleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteCourseField(courseName);
                courses.remove(courseName);
                Toast.makeText(getBaseContext(), "Deleted " + courseName, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });

        deleteBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(), "Well then...", Toast.LENGTH_LONG).show();
                editCoursePopUp();
            }
        });

        AlertDialog deleteDialog = deleteBuilder.create();
        deleteDialog.show();
    }
}
