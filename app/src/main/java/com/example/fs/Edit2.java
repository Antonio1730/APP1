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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit2 extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = "AddDrawItem";
    FloatingActionButton adddrawitem;
    Databasehelper mDatabaseHelper;
    Button backtomain;
    View view;
    static ListView drawlist;
    String projectname, filler, choosendrawitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adddrawitem = findViewById(R.id.adddrawitem);
        mDatabaseHelper = new Databasehelper(this);
        backtomain = findViewById(R.id.backtomain);
        view =findViewById(R.id.activity_edit2);
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

                 choosendrawitem = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + choosendrawitem);
                drawlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                drawlist.setSelector(android.R.color.holo_purple);



                /**String factor =Integer.toString(getMultiplier(choosendrawitem));
                Log.d(TAG, "factortobedelivered"+factor);

                Intent fillEdit3intent = new Intent(getApplicationContext(), Edit3.class);
                fillEdit3intent.putExtra("drawitemname", choosendrawitem);
                fillEdit3intent.putExtra("multiplier", factor);
                fillEdit3intent.putExtra("newprojectname", projectname);
                startActivity(fillEdit3intent);
                finish();**/
            }

        });
    }

    @Override
    public void onClick(View v) {

        mDatabaseHelper.deleteSpecificDrawItem(choosendrawitem);
        fillDrawItemList();

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

    public void snackydelete(View view){
        Snackbar areusure = Snackbar.make(view, "are you sure?",Snackbar.LENGTH_INDEFINITE);
        areusure.setDuration(1000);
        areusure.setAction("Delete", Edit2.this);
        areusure.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.editbutton).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.deletebutton) {
            Log.d(TAG, "Sncky wird jetzt ausgeführt");
            snackydelete(view);
        }

        return super.onOptionsItemSelected(item);
    }
}


