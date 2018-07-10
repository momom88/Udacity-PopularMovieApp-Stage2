package com.example.android.popularmovieapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.popularmovieapp.R;

public class ReviewsAdapter extends BaseAdapter {
    private final Context mContext;
    private final Reviews[] mReviews;

    // Constructor
    public ReviewsAdapter(Context context, Reviews[] reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public int getCount() {
        if (mReviews == null || mReviews.length == 0) {
            return -1;
        }
        return mReviews.length;
    }

    @Override
    public Object getItem(int position) {
        if (mReviews == null || mReviews.length == 0) {
            return null;
        }

        return mReviews[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        TextView textViewAuthor;
        TextView textViewReviews;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.reviews_list, null);
        }

        textViewAuthor = (TextView) listItemView.findViewById(R.id.reviews_name);
        textViewReviews = (TextView) listItemView.findViewById(R.id.reviews_content);

        textViewAuthor.setText(mReviews[position].getAuthor());
        textViewReviews.setText(mReviews[position].getContent());

        return listItemView;
    }
}

