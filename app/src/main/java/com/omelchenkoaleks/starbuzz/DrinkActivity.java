package com.omelchenkoaleks.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // получаем напиток из данных интента
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
        Drink drink = Drink.drinks[drinkId];

        // заполняем название напитка
        TextView name = findViewById(R.id.name);
        name.setText(drink.getName());

        // заполняем описание напитка
        TextView description = findViewById(R.id.dimensions);
        description.setText(drink.getDescription());

        // заполняем изображение напитка
        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
    }
}
