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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddDrawItem2 extends AppCompatActivity {

    private static String TAG = "AddDrawItem2";
    EditText drawitem;
    Button addDrawItem;
    Databasehelper mDatabaseHelper;
    String projectname, newDrawItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draw_item2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawitem = findViewById(R.id.drawitem);
        addDrawItem=findViewById(R.id.addDrawItem);
        mDatabaseHelper = new Databasehelper(this);


        addDrawItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDrawItem = drawitem.getText().toString();

                Intent receivedintent = getIntent();
                projectname = receivedintent.getStringExtra("projectname");
                Log.d(TAG, "projectname after receivedintent ="+projectname);



                if (drawitem.length() != 0 )  {

                    if (projectname.length() !=0){

                    addDrawItem(newDrawItem, projectname);
                    BacktoListIntent();

                    drawitem.setText("");

                }} else {
                    toastMessage("You need to set a name for your project");
                }




            }
        });

    }
    public void BacktoListIntent(){
        Intent intent = new Intent(this, AddDrawItem.class);
        intent.putExtra("projectname", projectname);
        startActivity(intent);
        finish();
    }
    public void addDrawItem(String newDrawItem, String projectname) {
        boolean insertData = mDatabaseHelper.addDrawItem(newDrawItem, projectname);

        if (insertData == true) {


            toastMessage("Data successfully added");
        } else {
            toastMessage("Something went wrong");
        }

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}