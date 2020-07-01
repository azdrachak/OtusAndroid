package com.github.azdrachak.otusandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.view.Repository

class MovieListViewModel : ViewModel() {

    private var moviesLiveData = MutableLiveData<List<MovieItem>>()
    val cachedMoviesLiveData: LiveData<List<MovieItem>>
    private val favoriteMoviesLiveData: LiveData<List<MovieItem>>
    private var selectedMovieLiveData = MutableLiveData<MovieItem>()
    private var errorLiveData = MutableLiveData<String>()
    var progress = MutableLiveData<Boolean>()

    private val repository = Repository()

    init {
        cachedMoviesLiveData = repository.getAllMovies()
        moviesLiveData.postValue(App.instance.items)
        favoriteMoviesLiveData = repository.getFavorites()
        progress.postValue(false)
    }

    val movies: LiveData<List<MovieItem>>
        get() = moviesLiveData

    val favoriteMovies: LiveData<List<MovieItem>>
        get() = favoriteMoviesLiveData

    val error: LiveData<String>
        get() = errorLiveData

    val selectedMovie: LiveData<MovieItem>
        get() = selectedMovieLiveData

    fun onMovieFavorite(movie: MovieItem) {
        repository.setFavoriteStatus(movie)
    }

    fun onMovieSelect(movieItem: MovieItem) {
        selectedMovieLiveData.postValue(movieItem)
    }

    fun moreMovies() {
        App.page++
        val message = App.instance.getTopMovies(App.page, progress)
        if (App.instance.error) {
            App.page--
            errorLiveData.postValue(message)
        } else {
            if (App.instance.appFirstRun) {
                moviesLiveData.value = App.instance.items
            } else cacheMovies(App.instance.items)
        }
    }

    fun cacheMovies(movies: List<MovieItem>) = repository.addMovies(movies)

    fun onErrorShow() {
        errorLiveData.value = null
        App.instance.error = false
    }
}