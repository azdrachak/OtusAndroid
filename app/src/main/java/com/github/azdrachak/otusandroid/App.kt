package com.github.azdrachak.otusandroid

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.model.db.MoviesDb
import com.github.azdrachak.otusandroid.model.pojo.discover.Discover
import com.github.azdrachak.otusandroid.model.retrofit.TmdbApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private lateinit var api: TmdbApi
    lateinit var discover: Discover
    var error = false
    val items: MutableList<MovieItem> = mutableListOf()
    lateinit var db: MoviesDb

    var appFirstRun = true

    companion object {
        lateinit var instance: App
            private set

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
        const val API_KEY = "3e5a35776c3a22fa052f2038c3f87db3"
        const val language = R.string.lang
        const val sortBy = "popularity.desc"
        var page = 0
        val apiPageSize = 20

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
        db = MoviesDb.getInstance(this.applicationContext)
    }

    fun getTopMovies(pageNumber: Int, progress: MutableLiveData<Boolean>): String {
        progress.postValue(true)
        instance.api.getCurrentTopFilms(
            API_KEY,
            resources.getString(language),
            sortBy,
            pageNumber
        ).enqueue(object : Callback<Discover?> {

            override fun onFailure(call: Call<Discover?>, t: Throwable) {
                discover = Discover()
                error = true
                progress.postValue(false)
            }

            override fun onResponse(call: Call<Discover?>, response: Response<Discover?>) {
                discover = response.body()!!
                populateMovies(discover)
                progress.postValue(false)
            }

        })

        return if (error) resources.getString(R.string.dataError) else ""
    }

    private fun initRetrofit() {
        val client = OkHttpClient()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(TmdbApi::class.java)
    }

    private fun populateMovies(discover: Discover) {
        discover.results?.forEach {
            val movieItem = MovieItem(
                movieId = it.id,
                title = it.title,
                description = it.overview,
                poster = 0,
                posterPath = IMAGE_BASE_URL + it.posterPath,
                isFavorite = false,
                popularity = it.popularity ?: 0.0
            )
            items.add(movieItem)
        }
    }
}
