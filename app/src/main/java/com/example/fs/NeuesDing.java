package com.example.fs;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class NeuesDing extends AppCompatActivity {
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








        addProject.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String newEntry = projectname.getText().toString();
                if (projectname.length() != 0){
                    addNeuesDing(newEntry);
                    projectname.setText("");
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
    public void newitem(){

    }
}
