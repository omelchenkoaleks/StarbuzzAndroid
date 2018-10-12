package com.omelchenkoaleks.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    // имя базы данных
    private static final String DB_NAME = "starbuzz";
    // версия базы данных
    private static final int DB_VERSION = 1;

    public StarbuzzDatabaseHelper(Context context) {
        // вызываем конструктрор суперкласса SQLiteOpenHelper и передаем ему имя и версию базы данных
        super(context, DB_NAME, null, DB_VERSION);
    }

    // вызывается при первоначальном создании базы данных
    // используем его для создания таблицы и втавки данных
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");

        // данные каждого напитка вставляются в отдельной строке
        insertDrink(sqLiteDatabase, "Latte", "Espresso and steamed milk", R.drawable.latte);
        insertDrink(sqLiteDatabase, "Cappuccino", "Espresso, hot milk and steamed-milk foam"
                , R.drawable.cappuccino);
        insertDrink(sqLiteDatabase, "Filter", "Our best drip coffee", R.drawable.filter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // метод, чтобы вставить данные нескольких напитков и не повторять код
    private static void insertDrink(SQLiteDatabase sqLiteDatabase, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        sqLiteDatabase.insert("DRINK", null, drinkValues);
    }
}
