package com.example.fs;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class draw extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    Databasehelper mDatabasehelper;
    TextView projectname, RandomItem;
    Button draw;
    ImageButton edit, delete;
    String selectedName;
    Switch rep;
    int selectedID;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectname = findViewById(R.id.projectname);
        mDatabasehelper = new Databasehelper(this);
        edit = findViewById(R.id.editbutton);
        delete = findViewById(R.id.deletebutton);
        draw = findViewById(R.id.draw);
        RandomItem = findViewById(R.id.RandomItem);
        rep = findViewById(R.id.rep);
        view =findViewById(R.id.Activity_draw);

        Intent receivedintent = getIntent();
        selectedName = receivedintent.getStringExtra("name");
        selectedID = receivedintent.getIntExtra("id", -1);
        projectname.setText(selectedName);

        SharedPreferences sp = getSharedPreferences("saveSwitchstate", MODE_PRIVATE);
        rep.setChecked(sp.getBoolean("value", true));

        rep.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (rep.isChecked()){
                   SharedPreferences.Editor editor = getSharedPreferences("saveSwitchstate", MODE_PRIVATE).edit();
                   editor.putBoolean("value", true);
                   editor.apply();
                   rep.setChecked(true);
               }
               else{
                   SharedPreferences.Editor editor = getSharedPreferences("saveSwitchstate", MODE_PRIVATE).edit();
                   editor.putBoolean("value", false);
                   editor.apply();
                   rep.setChecked(false);
               }
           }
        });


        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (rep.isChecked() == false)
                {
                    getrandom();
                }
                else {

                }
            }


        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent editintent = new Intent(draw.this, Edit.class);
                editintent.putExtra("name", selectedName);
                editintent.putExtra("id", selectedID);
                startActivity(editintent);
                finish();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snacky(view);

            }


        });
    }

    @Override
    public void onClick(View v) {

        mDatabasehelper.deleteDecision(selectedID, selectedName);
        Intent deleteintent = new Intent(draw.this, MainActivity.class);
        startActivity(deleteintent);
        finish();
    }


    public void getrandom(){

        Cursor data = mDatabasehelper.randomchoice(selectedName);
        int count = data.getCount();
        double generate = Math.random();
        double randomnumber = generate * count + 1;
        int randomInt = (int) randomnumber;
        data.moveToPosition((randomInt-1));
        RandomItem.setText(data.getString(1));

    }
    public void snacky(View view){
        Snackbar areusure = Snackbar.make(view, "are you sure?",Snackbar.LENGTH_INDEFINITE);
        areusure.setDuration(1000);
        areusure.setAction("Delete", this);

        areusure.show();
    }


}

