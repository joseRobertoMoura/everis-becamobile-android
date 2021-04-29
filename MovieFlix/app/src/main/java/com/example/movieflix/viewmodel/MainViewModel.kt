package com.example.movieflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieTendency
import com.example.movieflix.model.api.MovieFlixApiTask
import com.example.movieflix.model.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val movieFlixApiTask = MovieFlixApiTask()
    private val movieFlixRepository = MovieRepository(movieFlixApiTask)
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

    fun setList(numPage: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                movieFlixRepository.requestMoviesTendency(
                    numPage,
                    ::onRequestSuccess,
                    ::onRequestError
                )
            }
        }

    }

    private fun onRequestError(code: Int?, message: String?) {
        list.postValue(null)
    }

    private fun onRequestSuccess(tendency: MovieTendency) {
        totalPages.postValue(tendency.total_pages)
        tendency.result.forEach { listOfResults.add(it) }
        list.postValue(listOfResults)
    }
}