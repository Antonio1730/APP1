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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends AppCompatActivity {

    private static String TAG = "Edit";
    Databasehelper mDatabaseHelper;
    Button addEditedProject;
    EditText projectname;
    String  newprojectname, oldprojectname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new Databasehelper(this);
        projectname = findViewById(R.id.projectname);
        addEditedProject = findViewById(R.id.addEditedProject);


        Intent receivedIntent = getIntent();
        oldprojectname =(receivedIntent.getStringExtra("name"));
        final int id = receivedIntent.getIntExtra("id",-1);
        projectname.setText(oldprojectname);

        addEditedProject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newprojectname = projectname.getText().toString();

                if (projectname.length() != 0) {
                    if (getCount()==0){
                        editDing(newprojectname, id, oldprojectname);
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


    public void editDing(String newprojectname, Integer id, String oldprojectname) {
        Cursor data = mDatabaseHelper.geteditableDrawItems(oldprojectname);

        while (data.moveToNext()){
            int drawentryID = data.getInt(0);
            boolean editData2 = mDatabaseHelper.editProjectnameforDrawItems(newprojectname, drawentryID);
            if(editData2==true){
                Log.d(TAG,"everythings fine, just updated at drawitem"+drawentryID);
            }
        }

        boolean editData = mDatabaseHelper.editdata(newprojectname, id);

        if (editData==true )  {
                toastMessage(("Editing successful"));
        } else {
            toastMessage("Problem");
        }

        Intent intent = new Intent(getApplicationContext(), Edit2.class);
        intent.putExtra("projectname", projectname.getText().toString());
        startActivity(intent);
        finish();
    }
    public int getCount(){
        Cursor unique = mDatabaseHelper.checkunique(newprojectname);
        int count = unique.getCount();
        Log.d(TAG,"Count:"+count);
        return count;
    }

}
