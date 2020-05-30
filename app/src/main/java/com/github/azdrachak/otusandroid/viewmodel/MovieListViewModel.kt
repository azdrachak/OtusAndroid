package com.github.azdrachak.otusandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.model.MovieItem

class MovieListViewModel : ViewModel() {

    private var moviesLiveData = MutableLiveData<List<MovieItem>>()
    private var favoriteMoviesLiveData = MutableLiveData<List<MovieItem>>()
    private var selectedMovieLiveData = MutableLiveData<MovieItem>()
    private var errorLiveData = MutableLiveData<String>()

    init {
        moviesLiveData.postValue(App.instance.items)
        favoriteMoviesLiveData.postValue(App.instance.favouritesList)
    }

    val movies: LiveData<List<MovieItem>>
        get() = moviesLiveData

    val favoriteMovies: LiveData<List<MovieItem>>
        get() = favoriteMoviesLiveData

    val error: LiveData<String>
        get() = errorLiveData

    val selectedMovie: LiveData<MovieItem>
        get() = selectedMovieLiveData

    fun onMovieFavorite() {
        favoriteMoviesLiveData.postValue(App.instance.favouritesList)
        moviesLiveData.postValue(App.instance.items)
    }

    fun onMovieSelect(movieItem: MovieItem) {
        selectedMovieLiveData.postValue(movieItem)
    }

    fun moreMovies() {
        App.page++
        val message = App.instance.getTopMovies(App.page)

        if (App.instance.error) {
            App.page--
            errorLiveData.postValue(message)
        } else moviesLiveData.value = App.instance.items
    }

    fun onErrorShow() {
        errorLiveData.value = null
        App.instance.error = false
    }
}