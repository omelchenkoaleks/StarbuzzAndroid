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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    // эти переменные добавлены для того, чтобы объекты были доступны в методах
    // setUpFavoritesListView() and onDestroy()
    private SQLiteDatabase mSQLiteDatabase;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        setupOptionsListView();

        setupFavoritesListView();
    }

    // код, который относится к списковому представлению выносим в отдельный метод
    private void setupOptionsListView() {
        // создание OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if (position == 0) {
                            Intent intent = new Intent(TopLevelActivity.this,
                                    DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                };
        // добавляем слушатель к списковому представлению
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    // метод для заполнения списка list_favorites и реагирования на щелчки
    private void setupFavoritesListView() {
        // заполняем список list_favorites из курсора
        ListView listFavorites = findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            // получаем ссылку на базу данных
            mSQLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            // списковое представление list_favirites использует этот курсор для получения данных
            favoritesCursor = mSQLiteDatabase.query("DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);

            CursorAdapter favoriteAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            favoritesCursor,
                            new String[] {"NAME"},
                            new int[] {android.R.id.text1}, 0);

            listFavorites.setAdapter(favoriteAdapter);

        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        // переход к DrinkActivity при выборе напитка
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        });
    }

    /**
     * добавляем метод, который вызывается при возврате пользователя к TopLevelActivity
     * так метод changeCursor() будет заменять текущий курсор, связанный с курсором адаптера, новым
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newCursor = mSQLiteDatabase.query("DRINK",
                new String[] {"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);

        ListView listFavorites = findViewById(R.id.list_favorites);

        // курсор, используемый list_favorites, заменяется новым курсором
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        // значение favoritesCursor заменяется новым курсором, чтобы его можно было закрыть в методе
        // onDestroy() активности
        favoritesCursor = newCursor;
    }


    // закрываем курсор и базу данных в этом методе, потому что он вызывается перед уничтожением
    // активности и они уже не понадобятся
    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        mSQLiteDatabase.close();
    }
}
