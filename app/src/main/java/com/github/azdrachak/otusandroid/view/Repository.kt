package com.github.azdrachak.otusandroid.view

import androidx.lifecycle.LiveData
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.model.db.MoviesDb

class Repository {
    private val db = App.instance.db
    private val movieDao = db.getMovieDao()

    private val allMoviesLiveData: LiveData<List<MovieItem>>
    private val favoriteMovies: LiveData<List<MovieItem>>

    init {
        allMoviesLiveData = movieDao.getMovies()
        favoriteMovies = movieDao.getFavoriteMovies()
    }

    fun addMovies(movies: List<MovieItem>) {
        movies.forEach {
            val movie = movieDao.getMovieById(it.movieId!!).value
            val isFavorite = movie?.isFavorite
            MoviesDb.dbWriteExecutor.execute {
                movieDao.insertMovie(it)
                if (isFavorite != null) movieDao.updateFavoriteStatus(it.movieId, isFavorite)
            }
        }
    }

    fun getAllMovies(): LiveData<List<MovieItem>> = allMoviesLiveData

    fun getFavorites(): LiveData<List<MovieItem>> = favoriteMovies

    fun setFavoriteStatus(movie: MovieItem) = MoviesDb.dbWriteExecutor.execute {
        movieDao.updateFavoriteStatus(
            movie.movieId!!,
            movie.isFavorite
        )
    }

}
