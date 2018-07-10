package com.example.android.popularmovieapp.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable {
    private String mAuthor;
    private String mContent;

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public void importAuthor(String author) {
        mAuthor = author;
    }

    public void importContent(String content) {
        mContent = content;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
    }
}
