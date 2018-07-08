package com.example.android.popularmovieapp.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by marti on 5/6/2018.
 */

class MovieAdapter extends BaseAdapter {
    private final Context mContext;
    private final Movie[] mMovies;

    // Constructor
    public MovieAdapter(Context context, Movie[] movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        if (mMovies == null || mMovies.length == 0) {
            return -1;
        }
        return mMovies.length;
    }

    @Override
    public Movie getItem(int position) {
        if (mMovies == null || mMovies.length == 0) {
            return null;
        }

        return mMovies[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int widthImageMovie = 185;
        int heightImageMovie = 256;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext)
                .load(mMovies[position].getmPosterPath())
                .resize(widthImageMovie,heightImageMovie)
                .into(imageView);
        return imageView;
    }
}
