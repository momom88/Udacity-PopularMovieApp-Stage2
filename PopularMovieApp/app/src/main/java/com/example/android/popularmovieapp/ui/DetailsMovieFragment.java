package com.example.android.popularmovieapp.ui;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popularmovieapp.FetchMovieAsyncTask;
import com.example.android.popularmovieapp.R;
import com.example.android.popularmovieapp.utilities.NetworkUtils;
import com.example.android.popularmovieapp.utilities.OnTaskCompleted;
import com.example.android.popularmovieapp.utilities.OnTaskCompletedReviews;
import com.example.android.popularmovieapp.utilities.OnTaskCompletedTrailers;
import com.example.android.popularmovieapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMovieFragment extends Fragment {

    // LogTag
    private final String LOG = DetailsMovieFragment.class.getSimpleName();

    private ListView mListViewTrailers;

    private ListView mListViewReviews;

    //id to trailers and reviews
    private String mId;

    private TrailersVideoMovie[] mTrailers;

    public DetailsMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_movie, container, false);

        Movie movie;

        ImageView posterView = rootView.findViewById(R.id.poster);
        TextView titleView = rootView.findViewById(R.id.title_content);
        TextView releaseDateView = rootView.findViewById(R.id.release_date_content);
        TextView averageView = rootView.findViewById(R.id.vote_average_content);
        TextView overviewView = rootView.findViewById(R.id.overview_content);
        mListViewTrailers = (ListView) rootView.findViewById(R.id.listview_trailers);
        mListViewReviews = (ListView) rootView.findViewById(R.id.listview_reviews);

        Intent intent = getActivity().getIntent();
        movie = intent.getParcelableExtra(getString(R.string.parcel_movie));

        if (movie == null) {
            Bundle bundle = getArguments();
            movie = bundle.getParcelable(getString(R.string.parcel_movie));
        }

        Picasso.with(getActivity())
                .load(movie.getmPosterPath())
                .into(posterView);


        titleView.setText(movie.getTitle());
        releaseDateView.setText(movie.getDate());
        averageView.setText(movie.getDetailedVoteAverage());
        overviewView.setText(movie.getOverview());
        mId = movie.getId();

        if (savedInstanceState != null &&
                savedInstanceState.containsKey("trailers")) {
            // Get Movie objects
            Parcelable[] parcelables = savedInstanceState
                    .getParcelableArray("trailers");

            if (parcelables != null) {
                int numTrailers = parcelables.length;
                mTrailers = new TrailersVideoMovie[numTrailers];
                for (int i = 0; i < numTrailers; i++) {
                    mTrailers[i] = (TrailersVideoMovie) parcelables[i];
                }
                // Load movie objects into view
                mListViewTrailers.setAdapter(new TrailersAdapter(getContext(), mTrailers));

                Log.v(LOG, "Got movie info from savedInstanceState");
            }
        } else {
            TrailersFromApi();
            ReviewsFromApi();
            Log.i(LOG, "else");
        }
        mListViewTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailersVideoMovie currentTrailers = (TrailersVideoMovie) parent.getItemAtPosition(position);
                String idTrailers = currentTrailers.getKey();
                watchYoutubeTrailersVideo(getContext(),idTrailers);
            }
        });

        return rootView;
    }

    public static void watchYoutubeTrailersVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private void ReviewsFromApi() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            OnTaskCompletedReviews tastFinish = new OnTaskCompletedReviews() {
                @Override
                public void onTaskCompletedReviews(Reviews[] reviews) {
                    mListViewReviews.setAdapter(new ReviewsAdapter(getActivity(), reviews));
                }
            };
            ReviewsQueryTask task = new ReviewsQueryTask(tastFinish);
            task.execute(mId);
        }
    }

    private void TrailersFromApi() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            OnTaskCompletedTrailers tastFinish = new OnTaskCompletedTrailers() {
                @Override
                public void onTaskCompletedTrailers(TrailersVideoMovie[] trailersVideoMovies) {
                    mListViewTrailers.setAdapter(new TrailersAdapter(getActivity(), trailersVideoMovies));
                }
            };
            TrailersQueryTask task = new TrailersQueryTask(tastFinish);
            task.execute(mId);
        }
    }

    public class TrailersQueryTask extends AsyncTask<String, Void, TrailersVideoMovie[]> {

        //listener
        private final OnTaskCompletedTrailers mListener;

        public TrailersQueryTask(OnTaskCompletedTrailers tastFinish) {
            mListener = tastFinish;
        }

        @Override
        protected TrailersVideoMovie[] doInBackground(String... strings) {
            URL url = NetworkUtils.buildUrlTrailers(strings);

            String trailersJsonStr = null;
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                trailersJsonStr = response;
                Log.i(LOG, "response" + response);
            } catch (IOException e) {
                Log.e(LOG, "Error build url", e);
            }
            try {
                // Make sense of the JSON data
                Log.i(LOG, "openMovieJsonUtils");
                return OpenMovieJsonUtils.trailersDataFromJson(trailersJsonStr);
            } catch (JSONException e) {
                Log.e(LOG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TrailersVideoMovie[] trailersVideoMovies) {
            mListener.onTaskCompletedTrailers(trailersVideoMovies);
        }
    }

    public class ReviewsQueryTask extends AsyncTask<String, Void, Reviews[]> {

        //listener
        private final OnTaskCompletedReviews mListener;

        public ReviewsQueryTask(OnTaskCompletedReviews tastFinish) {
            mListener = tastFinish;
        }

        @Override
        protected Reviews[] doInBackground(String... strings) {
            URL url = NetworkUtils.buildUrlReview(strings);

            String reviewsJsonStr = null;
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                reviewsJsonStr = response;
                Log.i(LOG, "response" + response);
            } catch (IOException e) {
                Log.e(LOG, "Error build url", e);
            }
            try {
                // Make sense of the JSON data
                Log.i(LOG, "openMovieJsonUtils");
                return OpenMovieJsonUtils.reviewsDataFromJson(reviewsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Reviews[] reviews) {
            mListener.onTaskCompletedReviews(reviews);
        }
    }
}
