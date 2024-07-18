package com.example.tarek.tourguideapp.categories;


public class CategoryItem {

    private String categoryName;
    private int categoryImageResourceID;


    public CategoryItem(String catName, int catImage) {
        categoryName = catName;
        categoryImageResourceID = catImage;
    }

    public int getCategoryImageResourceID() {
        return categoryImageResourceID;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
