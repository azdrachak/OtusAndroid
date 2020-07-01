package com.github.azdrachak.otusandroid.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.azdrachak.otusandroid.model.MovieItem

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getMovies(): LiveData<List<MovieItem>>

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovies(): LiveData<List<MovieItem>>

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE movie_id = :movieId")
    fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM movies WHERE movie_id = :movieId LIMIT 1")
    fun getMovieById(movieId: Int): LiveData<MovieItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieItem>)
}
