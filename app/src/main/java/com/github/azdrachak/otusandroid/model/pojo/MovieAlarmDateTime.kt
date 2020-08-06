package com.github.azdrachak.otusandroid.model.pojo

import com.github.azdrachak.otusandroid.model.MovieItem

data class MovieAlarmDateTime(
    var movieId: Int = 0,
    var movieTitle: String = "",
    var year: Int = 0,
    var month: Int = 0,
    var day: Int = 0,
    var hour: Int = 0,
    var minute: Int = 0
) {
    var movieItem: MovieItem? = null
}
