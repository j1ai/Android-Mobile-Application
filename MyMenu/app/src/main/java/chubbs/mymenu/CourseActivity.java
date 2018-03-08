package chubbs.mymenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        ListView courselist = findViewById(R.id.courselist);
        courselist.setAdapter(adapter);
        Button addCourse = findViewById(R.id.addcourseButton);
        addCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                EditText input =  findViewById(R.id.courseinput);
                listItems.add(input.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
