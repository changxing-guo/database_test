package com.example.filepersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LitePalTest extends AppCompatActivity implements View.OnClickListener {

    private Button createButton;
    private Button insertButton;
    private Button updateButton;
    private Button deleteButton;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);

        createButton = (Button) findViewById(R.id.create_database);
        insertButton = (Button) findViewById(R.id.insert_database);
        updateButton = (Button) findViewById(R.id.update_database);
        deleteButton = (Button) findViewById(R.id.delete_database);
        queryButton = (Button) findViewById(R.id.query_database);

        createButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        queryButton.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_database:
                Connector.getDatabase();
                break;
            case R.id.insert_database:
                Animal animal = new Animal();
                animal.setName("lion");
                animal.setAge(5);
                animal.save();
                break;
            case R.id.update_database:
                //有很多种方法，自己实践
                Animal animal1 = new Animal();
                animal1.setAge(10);
                animal1.update(1);
                ContentValues values_update = new ContentValues();
                values_update.put("age", 20);
                DataSupport.update(Animal.class, values_update, 1);
                break;
            case R.id.delete_database:
                //DataSupport.deleteAll(Animal.class, "id > ?", "1");
                DataSupport.delete(Animal.class, 2);
                break;
            case R.id.query_database:
                List<Animal> animals = DataSupport.findAll(Animal.class);
                for (Animal an: animals) {
                    Log.d("gcx" , "id = " + an.getId());
                    //Log.d("gcx" , "name = " + an.getName());
                    //Log.d("gcx" , "age = " + an.getAge());
                }
                break;
        }
    }
}