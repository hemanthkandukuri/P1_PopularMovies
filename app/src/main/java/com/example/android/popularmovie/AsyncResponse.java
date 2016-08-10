package com.example.android.popularmovie;

import java.util.List;

/*
*
* Custom interface to execute
* a call back function that returns results
* from an async task
*
* */

public interface AsyncResponse {

    void onTaskCompleted(List<Movie> resultList);

}
