package com.example.fs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NeuesDing extends AppCompatActivity {
    private static String TAG = "NeuesDing";
    Databasehelper mDatabaseHelper;
    Button addProject;
    EditText projectname;
    String  newEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neues_ding);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new Databasehelper(this);
        projectname = findViewById(R.id.projectname);
        addProject = findViewById(R.id.addProject);



        addProject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               newEntry = projectname.getText().toString();


                if (projectname.length() != 0) {
                   if (getCount()==0){
                    addNeuesDing(newEntry);
                    projectname.setText("");
                } else {toastMessage("Decision already exists, please enter a different name");}
                }else {
                    toastMessage("You need to set a name for your project");
                }


            }
        });

    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void addNeuesDing(String newEntry) {
        boolean insertData = mDatabaseHelper.adddata(newEntry);

        if (insertData == true) {
            toastMessage("Data successfully added");
        } else {
            toastMessage("Something went wrong");
        }
        Intent intent = new Intent(getApplicationContext(), AddDrawItem2.class);
        intent.putExtra("projectname", projectname.getText().toString());
        startActivity(intent);
        finish();
    }
    public int getCount(){
        Cursor unique = mDatabaseHelper.checkunique(newEntry);
        int count = unique.getCount();
        Log.d(TAG,"Count:"+count);
        return count;
}}


