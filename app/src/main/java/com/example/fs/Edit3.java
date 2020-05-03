package com.example.fs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit3 extends AppCompatActivity {


    private static String TAG = "AddDrawItem2";
    EditText drawitem, factor;
    Button addDrawItem;
    Databasehelper mDatabaseHelper;
    String projectname, newDrawItem, filler;
    int i, factorvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draw_item2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawitem = findViewById(R.id.drawitem);
        addDrawItem=findViewById(R.id.addDrawItem);
        mDatabaseHelper = new Databasehelper(this);
        factor = findViewById(R.id.factor);

        projectname=getIntent().getStringExtra("newprojectname");
        drawitem.setText(getIntent().getStringExtra("drawitemname"));
        factor.setText(getIntent().getStringExtra("multiplier"));


        addDrawItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDrawItem = drawitem.getText().toString();
                String value = factor.getText().toString();


                if (drawitem.length() != 0 )  {
                    if(checkifnumeric(value)==true) {


                        for (i = 0; i < factorvalue; i++) {
                            addDrawItem(newDrawItem, projectname);
                        }
                        BacktoListIntent();

                        drawitem.setText("");

                    } else {
                        toastMessage("Your factor needs to be numeric");
                    }
                } else {
                    toastMessage("You need to set a name for your project");
                }

            }
        });

    }

    public void BacktoListIntent(){
        Intent intent = new Intent(this, AddDrawItem.class);
        intent.putExtra("projectname", projectname);
        startActivity(intent);

    }

    public boolean checkifnumeric(String value){
        try {
            factorvalue = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
