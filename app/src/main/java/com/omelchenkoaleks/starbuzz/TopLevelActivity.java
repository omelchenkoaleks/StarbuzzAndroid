package com.omelchenkoaleks.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        // нужно создать объект OnItemClickListener и реализовать метод onItemClick = тогда
        // варинаты списка будут реагировать на щелчки
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                // первый вариант в списковом предствлении
                if (position == 0) {
                    // запустить DrinkCategoryActivity, если пользователь щелкнул на варинате 0 (Drink)
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        // добавляем слушателя к списковому представлению - это обеспечивает получение оповещений
        // о том, что пользователь щелкает на списковом предствалении
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
}
