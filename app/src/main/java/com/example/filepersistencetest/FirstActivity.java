package com.example.filepersistencetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper databaseHelper;
    private Button createButton;
    private Button insertButton;
    private Button updateButton;
    private Button deleteButton;
    private Button queryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db",
                null, 2);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_database:
                databaseHelper.getWritableDatabase();
                break;
            case R.id.insert_database:
                SQLiteDatabase db_insert = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("author", "dengfanping");
                values.put("price", 100.00);
                values.put("pages", 600);
                values.put("name", "android 1");
                db_insert.insert("Book", null, values);
                values.clear();
                values.put("author", "dengfanping");
                values.put("price", 110.00);
                values.put("pages", 700);
                values.put("name", "android 2");
                db_insert.insert("Book", null, values);
                break;
            case R.id.update_database:
                Log.d("gcx", "update_database: ");
                SQLiteDatabase db_update = databaseHelper.getWritableDatabase();
                ContentValues values_update = new ContentValues();
                values_update.put("name", "android 1");
                values_update.put("author", "dengfanping");
                db_update.update("Book", values_update, "id = ?", new String[]{"1"});
                values_update.clear();
                values_update.put("name", "android 2");
                values_update.put("author", "dengfanping");
                db_update.update("Book", values_update, "id = ?", new String[]{"2"});
                break;
            case R.id.delete_database:
                Log.d("gcx", "delete_database: ");
                SQLiteDatabase db_delete = databaseHelper.getWritableDatabase();
                db_delete.delete("Book", "id > ?", new String[] {"2"});
                break;
            case R.id.query_database:
                SQLiteDatabase db_query = databaseHelper.getWritableDatabase();
                Cursor cursor = db_query.query("Book", null, null,
                        null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("gcx", "id: " + id);
                        Log.d("gcx", "name: " + name);
                        Log.d("gcx", "author: " + author);
                        Log.d("gcx", "pages: " + pages);
                        Log.d("gcx", "price: " + price);
                    } while (cursor.moveToNext());
                }

                Cursor cursor_test = db_query.query("Book", null, "id = ?",
                        new String[]{"1"}, null, null, null);
                cursor_test.moveToNext();
                int id1 = cursor_test.getInt(cursor_test.getColumnIndex("id"));
                Log.d("gcx", "id1: " + id1);
                cursor.close();
                break;
        }
    }
}