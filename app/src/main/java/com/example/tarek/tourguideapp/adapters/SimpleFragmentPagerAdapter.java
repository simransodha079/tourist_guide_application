package com.example.tarek.tourguideapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tarek.tourguideapp.fragment.FragmentItem;
import com.example.tarek.tourguideapp.locations.Location;
import com.example.tarek.tourguideapp.locations.LocationsData;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String currentCategory;
    private ArrayList<Location> locationsData;
    private int count;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, String category) {
        super(fm);
        this.context = context;
        currentCategory = category;
        setLocationsData();
        count = locationsData.size();
    }

    @Override
    public Fragment getItem(int position) {
        Location location;
        location = locationsData.get(position);
        FragmentItem fragmentItem;

        fragmentItem = new FragmentItem(); // because Fragment need new object each time
        String title = ++position + "/" + count + "\t\t\t";
        fragmentItem.setLocation(location, title);
        return fragmentItem;
    }

    @Override
    public int getCount() {
        return count;
    }

    private void setLocationsData() {
        locationsData = new LocationsData(context, currentCategory).getLocations();
    }


}
