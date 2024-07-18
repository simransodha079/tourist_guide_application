package com.example.tarek.tourguideapp.locations;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.tarek.tourguideapp.R;

import java.util.ArrayList;

public class LocationsData {

    private ArrayList<Location> locations;
    private Context context;
    private String currentCategory;

    public LocationsData(Context context, String category) {
        this.context = context;
        currentCategory = category;
        setLocations();
    }

    public void setLocations() {
        locations = new ArrayList<>();

        if (currentCategory.equals(context.getString(R.string.historical_places))) {
            setArray(R.array.historical_names,
                    R.array.historical_address,
                    R.array.historical_descriptions,
                    R.array.historical_images,
                    R.array.historical_sounds);
        } else if (currentCategory.equals(context.getString(R.string.museums))) {
            setArray(R.array.museums_names,
                    R.array.museums_address,
                    R.array.museums_descriptions,
                    R.array.museums_images,
                    R.array.museums_sounds);
        } else if (currentCategory.equals(context.getString(R.string.restaurants))) {
            setArray(R.array.restaurants_names,
                    R.array.restaurants_address,
                    R.array.restaurants_descriptions,
                    R.array.restaurants_images,
                    R.array.restaurants_sounds);
        } else if (currentCategory.equals(context.getString(R.string.islamics))) {
            setArray(R.array.islamics_names,
                    R.array.islamics_address,
                    R.array.islamics_descriptions,
                    R.array.islamics_images,
                    R.array.islamics_sounds);
        } else if (currentCategory.equals(context.getString(R.string.copts))) {
            setArray(R.array.copts_names,
                    R.array.copts_address,
                    R.array.copts_descriptions,
                    R.array.copts_images,
                    R.array.copts_sounds);
        } else if (currentCategory.equals(context.getString(R.string.beaches))) {
            setArray(R.array.beaches_names,
                    R.array.beaches_address,
                    R.array.beaches_descriptions,
                    R.array.beaches_images,
                    R.array.beaches_sounds);
        } else if (currentCategory.equals(context.getString(R.string.natural_places))) {
            setArray(R.array.natural_places_names,
                    R.array.natural_places_address,
                    R.array.natural_places_descriptions,
                    R.array.natural_places_images,
                    R.array.natural_places_sounds);
        } else if (currentCategory.equals(context.getString(R.string.country))) {
            locations.add(new Location(
                    context.getString(R.string.country),
                    context.getString(R.string.country_address),
                    context.getString(R.string.country_description),
                    R.drawable.egy_orthographic,
                    R.raw.egypt_description_sound));
        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setArray(int nameResource, int addressResource, int descriptionResource, int imagesResources, int soundResource) {
        String[] names = context.getResources().getStringArray(nameResource);
        String[] address = context.getResources().getStringArray(addressResource);
        String[] descriptions = context.getResources().getStringArray(descriptionResource);
        final TypedArray images = context.getResources().obtainTypedArray(imagesResources);
        final TypedArray sounds = context.getResources().obtainTypedArray(soundResource);
        final int DEFAULT_INT_VALUE = -1;
        for (int i = 0; i < names.length; i++) {
            locations.add(new Location(
                    names[i],
                    address[i],
                    descriptions[i],
                    images.getResourceId(i, DEFAULT_INT_VALUE),
                    sounds.getResourceId(i, DEFAULT_INT_VALUE)));
        }
        images.recycle(); //Many resources, such as TypedArrays, VelocityTrackers, etc., should be recycled (with a recycle() call) after use. This lint check looks for missing recycle() calls
        sounds.recycle();
    }

}
