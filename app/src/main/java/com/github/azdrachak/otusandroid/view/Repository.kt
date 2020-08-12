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
        val dbMovies = allMoviesLiveData.value ?: emptyList()
        movies.forEach {
            val movie = dbMovies.singleOrNull { movie -> movie.movieId == it.movieId }
            val isFavorite = movie?.isFavorite
            MoviesDb.dbWriteExecutor.execute {
                movieDao.insertMovie(it)
                if (isFavorite != null) movieDao.updateFavoriteStatus(it.movieId!!, isFavorite)
            }
        }
        App.instance.items.clear()
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
