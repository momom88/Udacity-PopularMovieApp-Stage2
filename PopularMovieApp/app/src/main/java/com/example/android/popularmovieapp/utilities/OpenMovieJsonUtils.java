package com.example.android.popularmovieapp.utilities;

import com.example.android.popularmovieapp.ui.Movie;
import com.example.android.popularmovieapp.ui.Reviews;
import com.example.android.popularmovieapp.ui.TrailersVideoMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marti on 5/10/2018.
 */

public final class OpenMovieJsonUtils {

    private static final String TAG_MOVIE_RESULTS = "results";

    //Movie information
    private static final String TAG_MOVIE_ID = "id";
    private static final String TAG_MOVIE_TITLE = "original_title";
    private static final String TAG_MOVIE_POSTER_PATH = "poster_path";
    private static final String TAG_MOVIE_OVERVIEW = "overview";
    private static final String TAG_MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String TAG_MOVIE_RELEASE_DATE = "release_date";

    private static final String TAG_TRAILERS_RESULTS = "results";

    //Trailers information
    private static final String TAG_TRAILERS_ID = "id";
    private static final String TAG_TRAILERS_NAME = "name";
    private static final String TAG_TRAILERS_KEY = "key";

    private static final String TAG_REVIEWS_RESULTS = "results";

    //Reviews information
    private static final String TAG_REVIEWS_AUTHOR = "author";
    private static final String TAG_REVIEWS_CONTENT = "content";

    public static Movie[] moviesDataFromJson(String movieJsonStr) throws JSONException {


        JSONObject jsonObject = new JSONObject(movieJsonStr);
        JSONArray resultArray = jsonObject.getJSONArray(TAG_MOVIE_RESULTS);

        Movie[] movies = new Movie[resultArray.length()];
        for (int i = 0; i < resultArray.length(); i++) {
            movies[i] = new Movie();

            JSONObject infoMovie = resultArray.getJSONObject(i);
            movies[i].importId(infoMovie.getString(TAG_MOVIE_ID));
            movies[i].importTitle(infoMovie.getString(TAG_MOVIE_TITLE));
            movies[i].importPosterPath(infoMovie.getString(TAG_MOVIE_POSTER_PATH));
            movies[i].importOverview(infoMovie.getString(TAG_MOVIE_OVERVIEW));
            movies[i].importVoteAverage(infoMovie.getDouble(TAG_MOVIE_VOTE_AVERAGE));
            movies[i].importDate(infoMovie.getString(TAG_MOVIE_RELEASE_DATE));
        }
        return movies;
    }

    public static TrailersVideoMovie[] trailersDataFromJson(String trailersJsonStr) throws JSONException {

        JSONObject jsonObject = new JSONObject(trailersJsonStr);
        JSONArray resultArray = jsonObject.getJSONArray(TAG_TRAILERS_RESULTS);

        TrailersVideoMovie[] trailers = new TrailersVideoMovie[resultArray.length()];
        for (int i = 0; i < resultArray.length(); i++) {
            trailers[i] = new TrailersVideoMovie();

            JSONObject infoTrailers = resultArray.getJSONObject(i);
            trailers[i].importId(infoTrailers.getString(TAG_TRAILERS_ID));
            trailers[i].importKey(infoTrailers.getString(TAG_TRAILERS_KEY));
            trailers[i].importName(infoTrailers.getString(TAG_TRAILERS_NAME));
        }
        return trailers;
    }

    public static Reviews[] reviewsDataFromJson(String reviewsJsonStr) throws JSONException {

        JSONObject jsonObject = new JSONObject(reviewsJsonStr);
        JSONArray resultArray = jsonObject.getJSONArray(TAG_REVIEWS_RESULTS);

        Reviews[] reviews = new Reviews[resultArray.length()];
        for (int i = 0; i < resultArray.length(); i++) {
            reviews[i] = new Reviews();

            JSONObject infoTrailers = resultArray.getJSONObject(i);
            reviews[i].importAuthor(infoTrailers.getString(TAG_REVIEWS_AUTHOR));
            reviews[i].importContent(infoTrailers.getString(TAG_REVIEWS_CONTENT));
        }
        return reviews;
    }
}
