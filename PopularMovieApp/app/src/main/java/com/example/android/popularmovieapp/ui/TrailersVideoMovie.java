package com.example.android.popularmovieapp.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailersVideoMovie implements Parcelable {

    private String mId;
    private String mName;
    private String mKey;

    /**
     * Constructor for a movie object
     */
    public TrailersVideoMovie() {
    }

    protected TrailersVideoMovie(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mKey = in.readString();
    }

    public static final Creator<TrailersVideoMovie> CREATOR = new Creator<TrailersVideoMovie>() {
        @Override
        public TrailersVideoMovie createFromParcel(Parcel in) {
            return new TrailersVideoMovie(in);
        }

        @Override
        public TrailersVideoMovie[] newArray(int size) {
            return new TrailersVideoMovie[size];
        }
    };

    public void importId(String id) {
        mId = id;
    }

    public void importName(String name) {
        mName = name;
    }

    public void importKey(String key) {
        mKey = key;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mKey);
    }
}
