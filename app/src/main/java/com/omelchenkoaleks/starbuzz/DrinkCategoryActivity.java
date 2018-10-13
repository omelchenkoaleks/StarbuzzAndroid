package com.omelchenkoaleks.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends Activity {

    // эти переменные добавляются для того, чтобы базу данных и курсор можно было закрыть в методе onDestroy()
    private SQLiteDatabase mSQLiteDatabase;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        // массив не нужет - теперь база данных есть
//        // создаем адаптер массива для отображения данных из массива
//        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                Drink.drinks);

        // адаптер массива связываем со списковым представлением методом setAdapter()
        ListView listDrinks = findViewById(R.id.list_drinks);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            // получить ссылку на базу данных
            mSQLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            // создать курсор
            mCursor = mSQLiteDatabase.query("DRINK",
                    new String[] {"_id", "NAME"},
                    null, null, null, null, null);

            // создаем адаптер курсора
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            listDrinks.setAdapter(listAdapter);

        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        // создаем слушателя
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listDrinks, View itemView, int position, long id) {
                // передаем напиток, выбранный пользователем, DrinkActivity
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                // вот эта важная передача по id
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        };
        // назначить слушатель для спискового представления
        listDrinks.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закрываем курсор и базу данных
        mCursor.close();
        mSQLiteDatabase.close();
    }
}
