package com.github.azdrachak.otusandroid.ilistener

import com.github.azdrachak.otusandroid.model.MovieItem

interface MovieItemListener {
    fun onMovieSelected(movieItem: MovieItem)
    fun onMovieFavorite(movieItem: MovieItem)
}
