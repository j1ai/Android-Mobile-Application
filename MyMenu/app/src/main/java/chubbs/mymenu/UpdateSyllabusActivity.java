package chubbs.mymenu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UpdateSyllabusActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<>();

    EditText item1, weight1;
    DatePicker datePicker;
    int day, month, year;


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
                    listItems.add(item1.getText().toString() + "                 "
                            + weight1.getText().toString() +
                            "%                    " +
                            day + "/" + month + "/" + year);
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

                listItems.remove(item1.getText().toString() + "                 "
                        + weight1.getText().toString() +
                        "%                    " +
                        day + "/" + month + "/" + year);
                adapter.notifyDataSetChanged();

                item1.getText().clear();
                weight1.getText().clear();


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
