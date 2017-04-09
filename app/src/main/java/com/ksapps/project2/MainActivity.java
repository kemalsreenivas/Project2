package com.ksapps.project2;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editLat,editLong,editId;
    Button buttonAdd,buttonView,buttonMap,buttonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editLat = (EditText)findViewById(R.id.editLat);
        editLong = (EditText)findViewById(R.id.editLong);
        editId = (EditText) findViewById(R.id.editId);
        buttonAdd = (Button)findViewById(R.id.button_Add);
        buttonView = (Button)findViewById(R.id.button2);
        buttonMap = (Button)findViewById(R.id.button3);
        buttonDel = (Button)findViewById(R.id.button);
        AddData();
        viewAll();
        displayMap();
        DeleteData();
    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData((editLat.getText().toString()),(editLong.getText().toString()));
                if(isInserted == true)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll(){
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount()==0){
                    //showMessage
                    showMessage("Error","No data found!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id :"+res.getString(0)+"\n");
                    buffer.append("Latitude :"+res.getString(1)+"\n");
                    buffer.append("Longitude :"+res.getString(2)+"\n");
                }
                 showMessage("Data",buffer.toString());
            }
        });
    }

    public void displayMap(){
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                res.moveToFirst();
                StringBuffer buffer1 = new StringBuffer();
                do{
                    buffer1.append(res.getString(1)+":");
                }while(res.moveToNext());
                res.moveToFirst();
                StringBuffer buffer2 = new StringBuffer();
                do {
                    buffer2.append(res.getString(2)+":");
                }while(res.moveToNext());

                res.moveToLast();
                String id = res.getString(0);

                /*showMessage("Latitudes",buffer1.toString());
                showMessage("Longitudes",buffer2.toString());
                showMessage("Id",id);*/

                String lat = buffer1.toString();
                String lng = buffer2.toString();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MapsActivity.class);
                Bundle extras = new Bundle();
                intent.putExtra("val1",lat);
                intent.putExtra("val2",lng);
                intent.putExtra("val3",id);
                startActivity(intent);
               //}
            }
        });
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void DeleteData() {
        buttonDel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
