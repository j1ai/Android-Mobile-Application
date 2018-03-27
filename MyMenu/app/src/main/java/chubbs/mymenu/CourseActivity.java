package chubbs.mymenu;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import chubbs.mymenu.models.Course;

public class CourseActivity extends BaseActivity {

    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private ListView courselist;
    private FloatingActionButton addCourse;
    private EditText input;
    private static final String TAG = "CourseActivity";
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        adapter=new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        courselist = findViewById(R.id.courselist);
        courselist.setAdapter(adapter);
        input =  findViewById(R.id.courseinput);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get courses
        mQuery = mFirestore.collection("courses");

        uid = getUid();


        addCourse = (FloatingActionButton) findViewById(R.id.addcourseButton);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCourse();
            }
        });

        Button nextStep = (Button) findViewById(R.id.nextButton);
        nextStep.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(CourseActivity.this, UpdateSyllabusActivity.class));
            }
        });
    }

    private void submitCourse() {
        final String cid = input.getText().toString();

        listItems.add(cid);
        adapter.notifyDataSetChanged();

        Course newCourse = new Course(cid);

        mFirestore.collection(uid).document("COURSES").collection(cid).add(newCourse);

    }


    // Ignore this part
    
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
