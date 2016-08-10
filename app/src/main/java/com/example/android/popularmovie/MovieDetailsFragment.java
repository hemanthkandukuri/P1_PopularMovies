package com.example.android.popularmovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //MovieDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailsFragment#} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    com.example.android.popularmovie.Movie movie;

    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movies_details")) {

            movie =  intent.getParcelableExtra("movies_details");
            DisplayInfo(rootView);

        }
        // Add textview 1
        return rootView;
    }

    private void DisplayInfo(View v) {
        TextView title = (TextView) v.findViewById(R.id.movie_title_view);
        ImageView image = (ImageView) v.findViewById(R.id.poster_image_view);
        TextView releaseDate = (TextView) v.findViewById(R.id.release_date);
        TextView ratings = (TextView) v.findViewById(R.id.ratings_view);
        TextView overview = (TextView) v.findViewById(R.id.synopsis_view);

        title.setText(movie.getTitle());
        Picasso.with(getActivity()).load(movie.getImage()).into(image);
        releaseDate.setText(movie.getReleaseDate());
        ratings.setText(movie.getRating() + "/10");
        overview.setText(movie.getDescription());


        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.reviewLayout);
        List<Review> reviews = movie.getReviews();
        for (Review review: reviews) {
            TextView textView1 = new TextView(v.getContext());
            textView1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                    AbsListView.LayoutParams.WRAP_CONTENT));
            textView1.setText(review.getContent());
            textView1.setBackgroundColor(0xff66ff66);
            textView1.setPadding(20, 20, 20, 20);
            linearLayout.addView(textView1);


        }
    }
}