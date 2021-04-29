package com.example.movieflix.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movieflix.model.Movie
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesListOberserver: Observer<List<Movie>>

    @Mock
    private lateinit var listObserver: Observer<List<Movie>>

    private lateinit var viewModel: MainViewModel

    @Test
    fun `Teste retorno api`(){
        val movie =  listOf(
            Movie("teste","teste","teste","teste")
        )
        viewModel = MainViewModel()
        viewModel.moviesList.observeForever(moviesListOberserver)

        viewModel.setList("1")

    }
}