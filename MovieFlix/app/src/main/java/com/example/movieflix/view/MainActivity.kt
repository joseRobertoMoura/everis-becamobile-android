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

class MainActivity() : AppCompatActivity(), ClickItemListener, View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private var numPage = 1
    private lateinit var totalPages:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        back_btn.setOnClickListener(this)
        next_btn.setOnClickListener(this)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        mainViewObserver(numPage.toString())

    }


    private fun mainViewObserver(numPage: String) {
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init(numPage)
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
        mainViewModel.total.observe(this, {
            totalPages = it
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

    override fun onClick(v: View) {
        val id = v.id

        if (id == R.id.back_btn){
            if (numPage > 1){
                numPage -= 1
                mainViewObserver(numPage.toString())
            }
        } else if(id == R.id.next_btn){
            if (numPage <= totalPages.toInt()){
                numPage += 1
                mainViewObserver(numPage.toString())
            }
        }
    }

}