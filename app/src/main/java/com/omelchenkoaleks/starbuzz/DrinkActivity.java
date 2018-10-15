package com.omelchenkoaleks.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // получаем напиток из данных интента
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        // создаем курсор
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            // получаем ссылку на базу данных
            SQLiteDatabase sqLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            // создаем курсор для получения из таблицы стобцов тех записей, у которых значение равно _id
            Cursor cursor = sqLiteDatabase.query("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)},
                    null, null, null);

            // переход к первой записи в курсоре
            if (cursor.moveToFirst()) {

                // получаем данные напитка из курсора
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                //Если столбец FAVORITE содержит 1, это соответствует значению true
                boolean isFavorite = (cursor.getInt(3) == 1);

                // заполняем название напитка
                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                // заполняем описание напитка
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                // заполнение изображения напитка
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                // Заполнение флажка любимого напитка
                CheckBox favirete = findViewById(R.id.favorite);
                favirete.setChecked(isFavorite);
            }

            // нужно закрыть базу данных и курсор
            cursor.close();
            sqLiteDatabase.close();

        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        // так было при использовании статического массива в java классе, а не базе данных
//        // заполняем название напитка
//        TextView name = findViewById(R.id.name);
//        name.setText(drink.getName());
//
//        // заполняем описание напитка
//        TextView description = findViewById(R.id.description);
//        description.setText(drink.getDescription());
//
//        // заполняем изображение напитка
//        ImageView photo = findViewById(R.id.photo);
//        photo.setImageResource(drink.getImageResourceId());
//        photo.setContentDescription(drink.getName());
    }

    // обновление базы данных по щелчку на флажке
    public void onFavoriteClicked(View view) {
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        // значение флажка добавляем в объект ContentValues с именем drinkValues
        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());

        // получение ссылки на базу данных и обновление столбца FAVORITE
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase sqLiteDatabase = starbuzzDatabaseHelper.getWritableDatabase();
            // таблица, данные которой нужно обновить
            sqLiteDatabase.update("DRINK",
                    // новые значения
                    // ContentValues с парами "имя/значение" обновляемых столбцов и значений
                    drinkValues,
                    // параметры conditionClause and conditionArguvents определяют записи, в
                    // которые вносятся изменения
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)});
            sqLiteDatabase.close();
        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
