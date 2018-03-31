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
import java.util.List;


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




        ListView list;
        //Set Up managing data

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        list = findViewById(R.id.list1);
        list.setAdapter(adapter);



        Button rmElement = findViewById(R.id.rmElement);
        rmElement.setOnClickListener(new OnClickListener(){
            public void onClick(View view){





            }
        });




    }
}
