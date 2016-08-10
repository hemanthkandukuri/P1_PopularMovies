package com.example.android.popularmovie;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/*MainActivity - UI and logic of view
* */

public class MovieGridFragment extends Fragment {
    private final String STORED_MOVIES = "stored_movies";
    private SharedPreferences prefs;
    private GridViewAdapter movieImageAdapter;
    String sortOrder;
    List<Movie> movies = new ArrayList<>();

    public MovieGridFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortOrder = prefs.getString(getString(R.string.display_preferences_sort_order_key),
                getString(R.string.display_preferences_sort_default_value));

        if(savedInstanceState != null){
            ArrayList<Movie> storedMovies = savedInstanceState.<Movie>getParcelableArrayList(STORED_MOVIES);
            movies.clear();
            movies.addAll(storedMovies);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieImageAdapter = new GridViewAdapter(
                getActivity(),
                R.layout.movie_grid_item_layout,
                R.id.grid_item_imageview,
                new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.movies_grid_view, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.main_gridView);
        gridView.setAdapter(movieImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie details = movies.get(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class)
                        .putExtra("movies_details", details);
                startActivity(intent);
            }

        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // get sort order to see if it has recently changed
        String prefSortOrder = prefs.getString(getString(R.string.display_preferences_sort_order_key),
                getString(R.string.display_preferences_sort_default_value));

        if(movies.size() > 0 && prefSortOrder.equals(sortOrder)) {
            updatePosterAdapter();
        }else{
            sortOrder = prefSortOrder;
            getMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Movie> storedMovies = new ArrayList<>();
        storedMovies.addAll(movies);
        outState.putParcelableArrayList(STORED_MOVIES, storedMovies);
    }

    private void getMovies() {
        ConnectMovieDb fetchMoviesTask = new ConnectMovieDb(new AsyncResponse() {
            @Override
            public void onTaskCompleted(List<Movie> results) {
                movies.clear();
                movies.addAll(results);
                updatePosterAdapter();
            }
        });
        fetchMoviesTask.execute(sortOrder);
    }

    /*
    ArrayAdapter of poster images*/
    private void updatePosterAdapter() {
        movieImageAdapter.clear();
        for(Movie movie : movies) {
            movieImageAdapter.add(movie.getImage());
        }
    }

}
