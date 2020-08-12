package com.github.azdrachak.otusandroid.model.pojo

import com.github.azdrachak.otusandroid.model.MovieItem
import java.util.*

data class MovieAlarmDateTime(
    var movieId: Int = 0,
    var movieTitle: String = ""
) {
    var movieItem: MovieItem? = null
    var dateTime: GregorianCalendar? = null
}
