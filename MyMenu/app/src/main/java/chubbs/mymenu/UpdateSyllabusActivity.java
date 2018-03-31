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
import android.widget.Spinner;
import java.util.ArrayList;
import chubbs.mymenu.models.Course;
import chubbs.mymenu.DataAccess.ManageData;
import chubbs.mymenu.models.Assessment;
import java.util.List;


public class UpdateSyllabusActivity extends BaseActivity {

    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();
    private static final String TAG = "SyllabusActivity";
    private List<Assessment> all;

    EditText item1, weight1;
    DatePicker datePicker;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db.addDocument("ASSESSMENTS");
        db.addDocument("TASKS");
        all = new ArrayList<>();
        setContentView(R.layout.update_syllabus);
        List<Course> courses = db.getAll_course();
        Log.d(TAG, "All Course" + courses.toString());
        String[] items = new String[courses.size()];
        int i =0;
        for (Course x : courses){
            items[i]=x.getCid();
            i++;

        }
//get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter2);




        ListView list;
        //Set Up managing data

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        list = findViewById(R.id.list1);
        list.setAdapter(adapter);

        db.getAllAssessments();
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
                    String course = dropdown.getSelectedItem().toString();

                    listItems.add(course +"     "+ name + "     "
                            + w +
                            "%     " +
                            date);

                    Assessment newAssessment = new Assessment(course,name,weightvalue,date);
                    db.addAssessment(newAssessment);
                    adapter.notifyDataSetChanged();
                    item1.getText().clear();
                    weight1.getText().clear();
                }
                db.getAllAssessments();

            }
        });
        Button rmElement = findViewById(R.id.rmElement);
        rmElement.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                db.getAllAssessments();
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
                String course = dropdown.getSelectedItem().toString();

                db.deleteAssessmentField(course,name);

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