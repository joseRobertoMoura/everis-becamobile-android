package com.example.movieflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieflix.model.api.MovieFlixApiTask
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieTendency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listOfResults: MutableList<Movie> = arrayListOf()

    private var totalPages = MutableLiveData<String>()
    private var list = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = list
    val total: LiveData<String>
        get() = totalPages

    fun init(numPage: String) {
        setList(numPage)
    }

    private fun setList(numPage: String) {
        Thread {
            val call = MovieFlixApiTask.retrofitApi()
                .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)
            call.enqueue(object : Callback<MovieTendency> {
                override fun onResponse(
                    call: Call<MovieTendency>,
                    response: Response<MovieTendency>
                ) {
                    if (response.isSuccessful) {
                        totalPages.postValue(response.body()?.total_pages)
                        response.body()?.result?.forEach { listOfResults.add(it) }
                        list.postValue(listOfResults)
                    }
                }

                override fun onFailure(call: Call<MovieTendency>, t: Throwable) {
                    list.postValue(null)
                }
            })
        }.start()

        listOfResults.clear()
    }
}