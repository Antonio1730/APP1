package com.example.fs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG="MainActivity";
    ListView itemList;
    Databasehelper mDatabasehelper;
    FloatingActionButton addItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemList = (ListView) findViewById(R.id.itemList);
        mDatabasehelper = new Databasehelper(this);
        addItem = findViewById(R.id.addItem);

        fillList();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNeuesDing();
            }
        });
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on "+name);

                Cursor data = mDatabasehelper.getDecisionID(name);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);

                }
                if(itemID>-1)
                {
                    Log.d(TAG, "onItemClick: The ID is: "+itemID);
                    Intent choiceintent = new Intent(MainActivity.this, draw.class);
                    choiceintent.putExtra("id",itemID);
                    choiceintent.putExtra("name",name);

                    startActivity(choiceintent);
                }
                else {
                    toastMessage("No ID with that name");
                }
            }

        });



    }
        private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

         public void openNeuesDing()
         {
             Intent intent = new Intent(this, NeuesDing.class);
             startActivity(intent);
             finish();

         }

         public void fillList()
         {
             Log.d(TAG, "populateListView: Displaying data in the ListView.");

             Cursor data = mDatabasehelper.getDecisions();
             ArrayList<String> listData = new ArrayList<>();
             while(data.moveToNext()){
                 listData.add(data.getString(1));
             }
             ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
             itemList.setAdapter(adapter);
         }














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
