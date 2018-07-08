package com.example.android.popularmovieapp.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TrailersAdapter extends BaseAdapter {
    private final Context mContext;
    private final TrailersVideoMovie[] mTrailersVideoMovie;

    // Constructor
    public TrailersAdapter(Context context, TrailersVideoMovie[] trailersVideoMovies) {
        mContext = context;
        mTrailersVideoMovie = trailersVideoMovies;
    }

    @Override
    public int getCount() {
        if (mTrailersVideoMovie == null || mTrailersVideoMovie.length == 0) {
            return -1;
        }
        return mTrailersVideoMovie.length;
    }

    @Override
    public Object getItem(int position) {
        if (mTrailersVideoMovie == null || mTrailersVideoMovie.length == 0) {
            return null;
        }

        return mTrailersVideoMovie[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        TextView textView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.trailers_list, null);
        }
        textView = (TextView) listItemView.findViewById(R.id.trailer_name);

        textView.setText(mTrailersVideoMovie[position].getName());
        return listItemView;
    }
}
