package com.example.movieflix.api


import com.example.movieflix.model.MovieDetail
import com.example.movieflix.model.MovieTendency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFlixApi {

    @GET("trending/movie/week")
    fun getListTndency(@Query("api_key") chaveApi:String): Call<MovieTendency>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String?, @Query("api_key") chaveApi:String): Call<MovieDetail>

}