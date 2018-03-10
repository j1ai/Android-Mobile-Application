package chubbs.mymenu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

        FloatingActionButton addCourse = (FloatingActionButton) findViewById(R.id.addcourseButton);
        addCourse.setOnClickListener(new OnClickListener(){
            public void onClick(View view){

                EditText input =  findViewById(R.id.courseinput);
                listItems.add(input.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Button nextStep = (Button) findViewById(R.id.nextButton);
        nextStep.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(CourseActivity.this, UpdateSyllabusActivity.class));
            }
        });
    }

    public ArrayList<String> loadJSONFromAsset() {
        ArrayList<String> courseList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("course.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("courses");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                courseList.add(jo_inside.getString("key"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courseList;
    }

}
