package com.example.movieflix.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.model.api.MovieFlixApiTask
import com.example.movieflix.model.helper.SharedPreferences
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieDetail
import com.example.movieflix.model.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val movieFlixApiTask = MovieFlixApiTask()
    private val movieFlixRepository = MovieRepository(movieFlixApiTask)


    private var detailObject = MutableLiveData<MovieDetail>()
    val moviesDetail: LiveData<MovieDetail>
        get() = detailObject

    fun init(movie: Movie?, context: Context) {
        val id: String? = movie?.id
        setObject(id, context)
    }

    private fun setObject(id: String?, context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                movieFlixRepository.requestMovieDetails(
                    id,
                    ::onRequestSuccess,
                    ::onRequestError
                )
            }
        }
    }

    private fun onRequestError(code: Int?, message: String?) {
        detailObject.postValue(null)
    }

    private fun onRequestSuccess(movieDetail: MovieDetail) {

        val detail = MovieDetail(
            movieDetail.original_title.toString(),
            movieDetail.poster_path.toString(),
            movieDetail.overview.toString(),
            movieDetail.original_name.toString()
        )

        detailObject.postValue(detail)

    }
}
