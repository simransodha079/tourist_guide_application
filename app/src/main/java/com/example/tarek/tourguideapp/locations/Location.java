package com.example.tarek.tourguideapp.locations;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    private String locationName;
    private String locationAddress;
    private String locationDescription;
    private int locationImageResourceId;
    private int locationSoundResourceId;

    public Location(String name, String address, String description, int imageId, int soundResource) {
        locationName = name;
        locationAddress = address;
        locationDescription = description;
        locationImageResourceId = imageId;
        locationSoundResourceId = soundResource;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        locationAddress = in.readString();
        locationDescription = in.readString();
        locationImageResourceId = in.readInt();
        locationSoundResourceId = in.readInt();
    }

    public String getName() {
        return locationName;
    }

    public String getAddress() {
        return locationAddress;
    }

    public String getDescription() {
        return locationDescription;
    }

    public int getImageResourceId() {
        return locationImageResourceId;
    }

    public int getSoundResourceId() {
        return locationSoundResourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeString(locationAddress);
        dest.writeString(locationDescription);
        dest.writeInt(locationImageResourceId);
        dest.writeInt(locationSoundResourceId);
    }
}
