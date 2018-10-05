package com.omelchenkoaleks.starbuzz;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    // каждый объект в массиве содержит три поля
    public static final Drink[] drinks = {
        new Drink("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
        new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable. cappuccino),
        new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };

    // у каждого напитка есть имя, описание и фотография
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
