package com.github.azdrachak.otusandroid.view

import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.model.db.MoviesDb

class Repository {
    private val db = App.instance.db
    private val movieDao = db.getMovieDao()

    fun addMovies(movies: List<MovieItem>) {
        movies.forEach {
            if (movieDao.getMovieById(it.movieId!!).value == null) {
                MoviesDb.dbWriteExecutor.execute {
                    movieDao.insertMovie(it)
                }
            }
        }
    }
}
