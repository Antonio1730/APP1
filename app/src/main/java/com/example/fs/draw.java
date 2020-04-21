package com.example.fs;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class draw extends AppCompatActivity {

    private static String TAG="MainActivity";
    Databasehelper mDatabasehelper;
    TextView projectname;
    Button draw;
    ImageButton edit, delete;
    String selectedName;
    int selectedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectname     = findViewById(R.id.projectname);
        mDatabasehelper = new Databasehelper(this);
        edit            = findViewById(R.id.editbutton);
        delete          = findViewById(R.id.deletebutton);
        draw            = findViewById(R.id.draw);


            Intent receivedintent   = getIntent();

                selectedName        = receivedintent.getStringExtra("name");
                selectedID          = receivedintent.getIntExtra("id",-1);
                projectname.setText(selectedName);


        Log.d(TAG, "onCreate: "+selectedName);


        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                mDatabasehelper.deleteDecision(selectedID, selectedName);
                Intent deleteintent = new Intent(draw.this, MainActivity.class);
                startActivity(deleteintent);
                finish();
            }


        });
    }
    private void toastMessage(String message) {
    }
    public void edititem(){


    }
}

