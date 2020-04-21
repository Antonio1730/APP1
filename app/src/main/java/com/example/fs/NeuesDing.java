package com.example.fs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NeuesDing extends AppCompatActivity {
    private static String TAG = "NeuesDing";
    Databasehelper mDatabaseHelper;
    Button addProject;
    EditText projectname;
    FloatingActionButton adddrawitem;
    TextView drawitem;
    ListView drawlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neues_ding);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new Databasehelper(this);
        projectname = findViewById(R.id.projectname);
        addProject  = findViewById(R.id.addProject);
        adddrawitem = findViewById(R.id.adddrawitem);
        drawitem    = findViewById(R.id.drawitem);
        drawlist    = findViewById(R.id.drawlist);
        fillDrawItemList();


        addProject.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String newEntry = projectname.getText().toString();
                if (projectname.length() != 0){
                    addNeuesDing(newEntry);
                    projectname.setText("");
                    }
                else{ toastMessage("You need to set a name for your project");
                }
            }
        });

        adddrawitem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newDrawEntry = drawitem.getText().toString();
                if (drawitem.length() != 0){
                    addDrawItem(newDrawEntry);
                    drawitem.setText("");
                }
                else{ toastMessage("You need to fill in the text field");
                }


            }
        });
    }



    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void addNeuesDing(String newEntry){
        boolean insertData = mDatabaseHelper.adddata(newEntry);

        if (insertData==true){
            toastMessage("Data successfully added");
        }else {
            toastMessage("Something went wrong");
        }
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
        finish();
    }

    public void addDrawItem(String newDrawEntry){
        boolean insertData = mDatabaseHelper.addDrawItem(newDrawEntry);

        if (insertData==true){
            toastMessage("Data successfully added");
        }else {
            toastMessage("Something went wrong");
        }

        finish();
    }
    public void fillDrawItemList(){

            Log.d(TAG, "populateListView: Displaying data in the ListView.");

            Cursor data = mDatabaseHelper.getDrawItems();
            ArrayList<String> listData = new ArrayList<>();
            while(data.moveToNext()){
                listData.add(data.getString(1));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            drawlist.setAdapter(adapter);

    }

}
