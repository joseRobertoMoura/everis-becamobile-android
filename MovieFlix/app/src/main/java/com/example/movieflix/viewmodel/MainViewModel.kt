package com.example.movieflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieflix.api.MovieFlixApiTask
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieTendency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var list = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = list

    fun init() {
        setList()
    }

    private fun setList() {
        Thread {
            val call = MovieFlixApiTask.retrofitApi()
                .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae")
            call.enqueue(object : Callback<MovieTendency> {
                override fun onResponse(
                    call: Call<MovieTendency>,
                    response: Response<MovieTendency>
                ) {
                    if (response.isSuccessful) {
                        val listOfResults: MutableList<Movie> = arrayListOf()
                        response.body()?.result?.forEach { listOfResults.add(it) }
                        list.postValue(listOfResults)
                    }
                }

                override fun onFailure(call: Call<MovieTendency>, t: Throwable) {
                    list.postValue(null)
                }
            })
        }.start()
    }
}