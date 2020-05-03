package com.example.fs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit2 extends AppCompatActivity {

    private static String TAG = "AddDrawItem";
    FloatingActionButton adddrawitem;
    Databasehelper mDatabaseHelper;
    Button backtomain, refreshtest;
    static ListView drawlist;
    String projectname, filler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draw_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adddrawitem = findViewById(R.id.adddrawitem);
        mDatabaseHelper = new Databasehelper(this);
        backtomain = findViewById(R.id.backtomain);
        drawlist = findViewById(R.id.drawlist);

        SharedPreferences sp = getSharedPreferences("saveIntentExtra", MODE_PRIVATE);
        filler = getIntent().getStringExtra("projectname");
        if (filler!= null) {

            projectname = filler;
            Log.d(TAG, "projectname after receivedintent =" + projectname);
            sp.edit().putString("Extra", projectname).apply();
        }
        else{
            projectname = getSharedPreferences("saveIntentExtra", MODE_PRIVATE).getString("Extra","ohneee");
        }

        fillDrawItemList();

        adddrawitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEdit3();
            }
        });
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BacktoMain();
            }
        });

        drawlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, int i, long l) {

                String choosendrawitem = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + choosendrawitem);

                String factor =Integer.toString(getMultiplier(choosendrawitem));
                Log.d(TAG, "factortobedelivered"+factor);

                Intent fillEdit3intent = new Intent(getApplicationContext(), Edit3.class);
                fillEdit3intent.putExtra("drawitemname", choosendrawitem);
                fillEdit3intent.putExtra("multiplier", factor);
                fillEdit3intent.putExtra("newprojectname", projectname);
                startActivity(fillEdit3intent);
                finish();
            }

    });
    }
    public void BacktoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toEdit3(){
        Intent intent = new Intent(this, Edit3.class);
        intent.putExtra("newprojectname", projectname);
        startActivity(intent);
        finish();

    }
    public void fillDrawItemList() {

        Log.d(TAG, "populateListView: Displaying data in the ListView."+ projectname);

        Cursor data = mDatabaseHelper.getDrawItems(projectname);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        drawlist.setAdapter(adapter);

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public int getMultiplier(String choosendrawitem){
        Cursor multi = mDatabaseHelper.getFactor(choosendrawitem);
        int i =multi.getCount();
        Log.d(TAG,"factorvaluefromdb="+i);
        return i;



    }
}


