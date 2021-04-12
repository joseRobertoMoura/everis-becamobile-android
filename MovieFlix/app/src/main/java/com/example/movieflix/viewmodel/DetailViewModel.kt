package com.example.movieflix.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieflix.model.api.MovieFlixApiTask
import com.example.movieflix.model.helper.SharedPreferences
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DetailViewModel : ViewModel() {
    lateinit var sharedPreferences: SharedPreferences


    private var check = MutableLiveData<Boolean>()
    val moviesList: LiveData<Boolean>
        get() = check

    fun init(movie: Movie?, context: Context) {
        val id: String? = movie?.id
        setObject(id, context)
    }


    private fun setObject(id: String?, context: Context) {
        try {
            Thread {
                sharedPreferences = SharedPreferences(context)
                val call = MovieFlixApiTask.retrofitApi()
                    .getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
                call.enqueue(object : Callback<MovieDetail> {
                    override fun onResponse(
                        call: Call<MovieDetail>,
                        response: Response<MovieDetail>
                    ) {
                        if (response.isSuccessful) {

                            sharedPreferences.storeString(
                                "original_name",
                                response.body()?.original_name.toString()
                            )
                            sharedPreferences.storeString(
                                "original_title",
                                response.body()?.original_title.toString()
                            )
                            sharedPreferences.storeString(
                                "poster_path",
                                response.body()?.poster_path.toString()
                            )
                            sharedPreferences.storeString(
                                "overview",
                                response.body()?.overview.toString()
                            )

                            check.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                        check.postValue(false)
                    }
                })
            }.start()
        } catch (e: Exception) {
            check.postValue(false)
            println(e.message)
        }
    }
}