package com.github.azdrachak.otusandroid.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.azdrachak.otusandroid.model.MovieItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [MovieItem::class], version = 3)
abstract class MoviesDb : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {

        private val NUMBER_OF_THREADS = 4
        val dbWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        @Volatile
        private var INSTANCE: MoviesDb? = null

        fun getInstance(context: Context): MoviesDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDb::class.java,
                    "movies_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
