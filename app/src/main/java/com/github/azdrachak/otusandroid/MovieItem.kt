package com.github.azdrachak.otusandroid

data class MovieItem(
    val title: String?,
    val description: String?,
    val poster: Int,
    var isFavorite: Boolean,
    var isVisited: Boolean
)
