package com.example.fs;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class draw extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    Databasehelper mDatabasehelper;
    TextView projectname, RandomItem, howmanyleft;
    Button draw ;
    ImageButton edit, delete;
    String selectedName, generatedItem;
    Switch rep;
    int selectedID, hiddenid;
    View view;
    ListView history;
    int count;

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
        history =findViewById(R.id.history);
        history.setVisibility(View.GONE);
        howmanyleft = findViewById(R.id.howmanyleft);

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
                   Log.d(TAG,"Switch checked");
               }
               else{
                   SharedPreferences.Editor editor = getSharedPreferences("saveSwitchstate", MODE_PRIVATE).edit();
                   editor.putBoolean("value", false);
                   editor.apply();
                   rep.setChecked(false);
                   Log.d(TAG,"Switch unchecked");

               }
           }
        });

        final ArrayList<String> listData = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ColorDrawable[] color = {new ColorDrawable(Color.argb(250,109, 70,107)), new ColorDrawable(Color.WHITE)};
                TransitionDrawable trans = new TransitionDrawable(color);
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadius(40.0f);
                RandomItem.setBackground(shape);
                /**RandomItem.setBackground(trans);
                trans.startTransition(2000);**/

                if (rep.isChecked() == false)
                {
                    getrandom();
                    howmanyleft.setText("");

                }
                else {

                    getrandomwhiledeleting();
                    Log.d(TAG, "added item "+generatedItem);
                    Log.d(TAG, "added id "+hiddenid);

                    if(listData.contains(Integer.toString(hiddenid))==false)
                    {
                        int howmany = ((count) - history.getCount());
                        RandomItem.setText(generatedItem);
                        RandomItem.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
                        listData.add(Integer.toString(hiddenid));
                        history.setAdapter(adapter);
                        howmanyleft.setText(Integer.toString(howmany));
                        Log.d(TAG, "added id "+hiddenid);
                    }
                    else{
                        int howmany = ((count) - history.getCount());
                        getrandomwhiledeleting();
                        RandomItem.setText("try again");
                        howmanyleft.setText(Integer.toString(howmany));
                       /** if(howmany>1) {
                            howmanyleft.setText(howmany);
                        }
                        else if (howmany==1){
                            howmanyleft.setText(howmany);
                        }
                         else {
                            toastMessage("there are no items remaining");
                        }**/
                    }

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

                snackydelete(view);
            }


        });

    }


    @Override
    public void onClick(View v) {

        mDatabasehelper.deleteDecision(selectedID, selectedName);
        mDatabasehelper.deleteDrawItems(selectedName);
        Intent deleteintent = new Intent(draw.this, MainActivity.class);
        startActivity(deleteintent);
        finish();
    }


    public void getrandom(){

        Cursor data = mDatabasehelper.randomchoice(selectedName);
        count = data.getCount();
        double generate = Math.random();
        double randomnumber = generate * count + 1;
        int randomInt = (int) randomnumber;
        data.moveToPosition((randomInt-1));
        hiddenid = data.getInt(0);
        RandomItem.setText(data.getString(1));
        RandomItem.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
        Log.d(TAG,"getrandommessage activated");

    }

    public void getrandomwhiledeleting(){

        Cursor data = mDatabasehelper.randomchoice(selectedName);
        count = data.getCount();
        double generate = Math.random();
        double randomnumber = generate * count + 1;
        int randomInt = (int) randomnumber;
        data.moveToPosition((randomInt-1));
        hiddenid = data.getInt(0);
        generatedItem=data.getString(1);
        Log.d(TAG,"getrandommessagewhiledeleting activated");


    }
    public void snackydelete(View view){
        Snackbar areusure = Snackbar.make(view, "are you sure?",Snackbar.LENGTH_INDEFINITE);
        areusure.setDuration(1000);
        areusure.setAction("Delete", this);

        areusure.show();
    }



    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}

