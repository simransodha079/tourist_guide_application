package com.example.tarek.tourguideapp.categories;

import android.content.Context;

import com.example.tarek.tourguideapp.R;

import java.util.ArrayList;

public class CategoriesData {
    private ArrayList<CategoryItem> categories;
    private Context context;

    public CategoriesData(Context context) {
        this.context = context;
        setCategories();
    }

    public void setCategories() {
        categories = new ArrayList<>();

        categories.add(new CategoryItem(context.getString(R.string.historical_places), R.drawable.icons8_pyramids));
        categories.add(new CategoryItem(context.getString(R.string.museums), R.drawable.icons8_parliament_48));
        categories.add(new CategoryItem(context.getString(R.string.natural_places), R.drawable.icons8_field_48));
        categories.add(new CategoryItem(context.getString(R.string.islamics), R.drawable.icons8_mosque_48));
        categories.add(new CategoryItem(context.getString(R.string.copts), R.drawable.icons8_cathedral_48));
        categories.add(new CategoryItem(context.getString(R.string.beaches), R.drawable.icons8_beach_48));
        categories.add(new CategoryItem(context.getString(R.string.restaurants), R.drawable.icons8_restaurant_building_48));
    }

    public ArrayList<CategoryItem> getCategories() {
        return categories;
    }
}
