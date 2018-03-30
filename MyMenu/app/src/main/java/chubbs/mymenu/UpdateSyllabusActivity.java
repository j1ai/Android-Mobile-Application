package chubbs.mymenu;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chubbs.mymenu.DataAccess.ManageData;
import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Course;


public class UpdateSyllabusActivity extends BaseActivity {


    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();

    EditText item1, weight1;
    DatePicker datePicker;
    int day, month, year;
    private ManageData db;
    private List<Course> all_course;
    private static final String TAG = "CourseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView list;
        all_course = new ArrayList<>();
        //Set Up managing data
        db = new ManageData(this);
        db.addDocument("ASSESSMENTS");
        all_course = db.getAllCourses();
        Log.d(TAG, "DocumentSnapshotttttttttt " + all_course.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_syllabus);

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        list = findViewById(R.id.list1);
        list.setAdapter(adapter);


        Button addElement = findViewById(R.id.addElement);
        addElement.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                item1 =  findViewById(R.id.assessment1);
                weight1 = findViewById(R.id.weight1);

                datePicker = (DatePicker) findViewById(R.id.datePicker);
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1; //indexes start at 0
                year = datePicker.getYear();

                //error detection in text fields
                weight1.setError(null);
                item1.setError(null);

                String weight = weight1.getText().toString();
                String item = item1.getText().toString();
                boolean cancel = false;
                View focusView = null;

                //check if weight is correct and exists
                if (TextUtils.isEmpty(weight)) {
                    weight1.setError(getString(R.string.error_field_required));
                    focusView = weight1;
                    cancel = true;
                }
                else if (Integer.parseInt(weight) < 0 || Integer.parseInt(weight) > 100) {
                    weight1.setError("0-100%");
                    focusView = weight1;
                    cancel = true;
                }

                if (TextUtils.isEmpty(item)) {
                    item1.setError(getString(R.string.error_field_required));
                    focusView = item1;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                }

                else{
                    String name = item1.getText().toString();
                    String w = weight1.getText().toString();
                    int weightvalue = Integer.parseInt(w);
                    String date = day + "/" + month + "/" + year;

                    listItems.add( name + "                 "
                            + w +
                            "%                    " +
                            date);

                    Assessment newAssessment = new Assessment(name,weightvalue,date);
                    db.addAssessment(newAssessment);
                    adapter.notifyDataSetChanged();

                    item1.getText().clear();
                    weight1.getText().clear();
                }


            }
        });
        Button rmElement = findViewById(R.id.rmElement);
        rmElement.setOnClickListener(new OnClickListener(){
            public void onClick(View view){



                item1 =  findViewById(R.id.assessment1);
                weight1 = findViewById(R.id.weight1);

                datePicker = (DatePicker) findViewById(R.id.datePicker);
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1; //indexes start at 0
                year = datePicker.getYear();

                String name = item1.getText().toString();
                String weight = weight1.getText().toString();
                int weightvalue = Integer.parseInt(weight);
                String date = day + "/" + month + "/" + year;

                listItems.remove( name + "                 "
                        + weight +
                        "%                    " +
                        date);

                adapter.notifyDataSetChanged();

                item1.getText().clear();
                weight1.getText().clear();


            }
        });

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view){

                startActivity(new Intent(UpdateSyllabusActivity.this, MainActivity.class));
            }
        });


    }
}
