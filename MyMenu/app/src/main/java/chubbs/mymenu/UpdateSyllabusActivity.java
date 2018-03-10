package chubbs.mymenu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UpdateSyllabusActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView list;

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

                EditText item1 =  findViewById(R.id.assessment1);
                EditText weight1 = findViewById(R.id.weight1);
                EditText dueDate = findViewById(R.id.dueDate);
                listItems.add(item1.getText().toString() + "                 "
                        + weight1.getText().toString() +
                        "%                    " + dueDate.getText().toString());
                adapter.notifyDataSetChanged();





            }
        });
        Button rmElement = findViewById(R.id.rmElement);
        rmElement.setOnClickListener(new OnClickListener(){
            public void onClick(View view){

                EditText item1 =  findViewById(R.id.assessment1);
                EditText weight1 = findViewById(R.id.weight1);
                EditText dueDate = findViewById(R.id.dueDate);
                listItems.remove(item1.getText().toString() + "                 "
                        + weight1.getText().toString() +
                        "%                    " + dueDate.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view){

                startActivity(new Intent(UpdateSyllabusActivity.this, MenuActivity.class));
            }
        });
    }

}
