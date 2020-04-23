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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddDrawItem extends AppCompatActivity {

    private static String TAG = "AddDrawItem";
    FloatingActionButton adddrawitem;
    Databasehelper mDatabaseHelper;
    Button backtomain;
    TextView drawitem;
    ListView drawlist;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_draw_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        adddrawitem = findViewById(R.id.adddrawitem);
        mDatabaseHelper = new Databasehelper(this);
        backtomain = findViewById(R.id.backtomain);
        drawitem = findViewById(R.id.drawitem);
        drawlist = findViewById(R.id.drawlist);

        Intent projectnameintent = getIntent();
        final String projectname = projectnameintent.getStringExtra("projectname");
        
        
        
        adddrawitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newDrawEntry = drawitem.getText().toString();


                if (drawitem.length() != 0){
                    addDrawItem(newDrawEntry, projectname);
                    drawitem.setText("");
                }
                else{ toastMessage("You need to fill in the text field");
                }

                
                
            }
        });
        
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                BacktoMain();
            }
        });

        
    }
    
    



    public void BacktoMain(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
        finish();  
    }
    
    public void addDrawItem(String newDrawEntry, String DId){
        boolean insertData = mDatabaseHelper.addDrawItem(newDrawEntry, DId);

        if (insertData==true){
            toastMessage("Data successfully added");
        }else {
            toastMessage("Something went wrong");
        }


    }

   /** public void fillDrawItemList(){
        String decision = projectname;
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = mDatabaseHelper.getDrawItems(decision);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        drawlist.setAdapter(adapter);

    }

}**/


    private void toastMessage(String data_successfully_added) {
    }


}
