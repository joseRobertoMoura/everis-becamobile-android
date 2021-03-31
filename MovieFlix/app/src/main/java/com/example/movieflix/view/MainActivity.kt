package com.example.movieflix.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.R
import com.example.movieflix.helper.ClickItemListener
import com.example.movieflix.model.Movie
import com.example.movieflix.view.MovieDetailActivity.Companion.EXTRA_MOVIE
import com.example.movieflix.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickItemListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init()
        mainViewObserver()

    }

    private fun mainViewObserver() {
        loadingVisibility(true)
        mainViewModel.moviesList.observe(this, {    list ->
            if (list != null) {
                loadingVisibility(false)
                moviesList.adapter = MoviesAdapter(list, this)
            } else {
                loadingVisibility(false)
                Toast.makeText(
                    this,
                    "Aconteceu um problema, tente novamente mais tarde!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun loadingVisibility(isLoading: Boolean) {
        progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun ClickItemMovie(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

}