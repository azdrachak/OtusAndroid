package com.github.azdrachak.otusandroid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.azdrachak.otusandroid.R

@Entity(tableName = "movies")
data class MovieItem(
    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0,

    @ColumnInfo(name = "movie_id")
    val movieId: Int?,

    val title: String?,

    val description: String?,

    val poster: Int = R.drawable.ic_image_black_24dp,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    val popularity: Double = 0.0
)
