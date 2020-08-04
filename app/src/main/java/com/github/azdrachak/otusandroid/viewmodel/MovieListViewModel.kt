package com.github.azdrachak.otusandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.model.pojo.MovieAlarmDateTime
import com.github.azdrachak.otusandroid.view.Repository

class MovieListViewModel : ViewModel() {

    val cachedMoviesLiveData: LiveData<List<MovieItem>>
    private val favoriteMoviesLiveData: LiveData<List<MovieItem>>
    private var selectedMovieLiveData = MutableLiveData<MovieItem>()
    private var errorLiveData = MutableLiveData<String>()
    var progress = MutableLiveData<Boolean>()
    private val _apiRequestTimeLiveData = MutableLiveData<Long>()
    private val _savedToDbLiveData = MutableLiveData<Boolean>()
    private val _alarmViewModel = MutableLiveData<MovieAlarmDateTime>()

    private val repository = Repository()

    init {
        cachedMoviesLiveData = repository.getAllMovies()
        favoriteMoviesLiveData = repository.getFavorites()
        progress.postValue(false)
    }

    val alarmViewModel: LiveData<MovieAlarmDateTime>
        get() = _alarmViewModel

    val apiRequestTimeLiveData: LiveData<Long>
        get() = _apiRequestTimeLiveData

    val savedToDbLiveData: LiveData<Boolean>
        get() = _savedToDbLiveData


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

    fun onSetAlarm(alarmData: MovieAlarmDateTime) {
        _alarmViewModel.postValue(alarmData)
    }

    fun moreMovies() {
        val cachedRecordsCount = cachedMoviesLiveData.value?.size ?: App.apiPageSize
        val pagesToGo = cachedRecordsCount / App.apiPageSize + 1
        App.page = 0
        while (App.page < pagesToGo) {
            App.page++
            val message = App.instance.getTopMovies(App.page, progress)
            if (App.instance.error) {
                App.page--
                errorLiveData.postValue(message)
            } else {
                _apiRequestTimeLiveData.postValue(System.currentTimeMillis())
            }
        }
    }

    fun cacheMovies(movies: List<MovieItem>) {
        repository.addMovies(movies)
        _savedToDbLiveData.postValue(true)
    }

    fun onErrorShow() {
        errorLiveData.value = null
        App.instance.error = false
    }
}