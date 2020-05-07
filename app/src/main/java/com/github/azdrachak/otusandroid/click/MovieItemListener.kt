package com.github.azdrachak.otusandroid.click

import com.github.azdrachak.otusandroid.MovieItem

interface MovieItemListener {
    fun onMovieSelected(movieItem: MovieItem)
    fun onMovieFavorite(movieItem: MovieItem)
}
