package chubbs.mymenu;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import chubbs.mymenu.models.Job;
import chubbs.mymenu.WeightedJob;
public class ViewScheduleActivity extends MainActivity {


    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();

    DatePicker datePicker;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db.addDocument("ASSESSMENTS");
        db.addDocument("COURSES");

        setContentView(R.layout.activity_view_schedule);
        super.createToolbar();




        final ListView list;
        //Set Up managing data

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        list = findViewById(R.id.list1);
        list.setAdapter(adapter);



        Button addElement = findViewById(R.id.addElement);
        addElement.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                datePicker = (DatePicker) findViewById(R.id.datePicker);
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1; //indexes start at 0
                year = datePicker.getYear();





                String date = day + "/" + month + "/" + year;
                List<Assessment> assessments = db.getAll_assessment();
                Assessment [] listOfAssess = new Assessment[assessments.size()];
                listOfAssess = assessments.toArray(listOfAssess);
                Job[] jobs = WeightedJob.convert(listOfAssess);
                ArrayList<Job> schedule = WeightedJob.schedule(jobs);
                Calendar myCalendar = new GregorianCalendar(year, month-1, day);
                int integerOfDay = myCalendar.get(Calendar.DAY_OF_YEAR);
                listItems.clear();
                for (Job j : schedule){
                    if (j.getStartTime()<= integerOfDay && j.getFinishTime() >= integerOfDay) {
                        listItems.add(j.getCourseCode() + "  " + j.getName());
                    }

                }


;
                adapter.notifyDataSetChanged();


                db.getAllAssessments();

            }
        });




    }
}
