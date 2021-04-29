package com.example.movieflix.model.repository

import com.example.movieflix.model.MovieDetail
import com.example.movieflix.model.MovieTendency
import com.example.movieflix.model.api.MovieFlixApiTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val movieFlixApiTask: MovieFlixApiTask) {

    companion object {
        private const val REQUEST_SUCCESS = 200
    }

    suspend fun requestMoviesTendency(
        numPage: String,
        onSuccess: (MovieTendency) -> Unit,
        onError: (Int?, String?) -> Unit
    ) {
        withContext(Dispatchers.Default) {
            val request = movieFlixApiTask.retrofitApi()
                .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)

            request.enqueue(object : Callback<MovieTendency> {

                override fun onResponse(
                    call: Call<MovieTendency>,
                    response: Response<MovieTendency>
                ) {
                    val responseCode = response.code()

                    if (responseCode == REQUEST_SUCCESS) {
                        val movieList: MovieTendency = response.body()!!
                        onSuccess.invoke(movieList)
                    } else {
                        onError.invoke(responseCode, null)
                    }
                }

                override fun onFailure(call: Call<MovieTendency>, t: Throwable) {
                    onError.invoke(null, t.toString())
                }

            })
        }

    }

    suspend fun requestMovieDetails(
        id: String?,
        onSuccess: (MovieDetail) -> Unit,
        onError: (Int?, String?) -> Unit
    ) {
        withContext(Dispatchers.Default) {
            val requestDetails = movieFlixApiTask.retrofitApi()
                .getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
            requestDetails.enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    val responseCodeDetail = response.code()
                    if (responseCodeDetail == REQUEST_SUCCESS) {
                        val movieDetail: MovieDetail = response.body()!!
                        onSuccess.invoke(movieDetail)
                    } else {
                        onError.invoke(responseCodeDetail, null)
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    onError.invoke(null, t.toString())
                }

            })

        }
    }
}