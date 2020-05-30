package com.github.azdrachak.otusandroid.model.retrofit

import com.github.azdrachak.otusandroid.model.pojo.discover.Discover
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("discover/movie")
    fun getCurrentTopFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Call<Discover>
}