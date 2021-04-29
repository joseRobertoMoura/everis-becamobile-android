package com.example.movieflix.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.movieflix.R
import com.example.movieflix.model.helper.SharedPreferences
import com.example.movieflix.model.Movie
import com.example.movieflix.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.abs_detail_item.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_MOVIE: String = "EXTRA_MOVIE"
    }

    private lateinit var sharedPreferences: SharedPreferences
    private var movie: Movie? = null

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_detail_item)

        getExtras()

        sharedPreferences = SharedPreferences(this)

        bindViews()

        abs_detail_back_btn.setOnClickListener(this)
    }

    private fun getExtras() {
        movie = intent.getParcelableExtra(EXTRA_MOVIE)
    }

    private fun bindViews() {
        detailViewModel =
            ViewModelProvider.NewInstanceFactory().create(DetailViewModel::class.java)
        detailViewModel.init(movie, this)
        visibilityProgressBar(true)
        visibilityLayoutDescricao(false)
        detailViewModel.moviesDetail.observe(this,{
            if (it != null){
                visibilityProgressBar(false)
                visibilityLayoutDescricao(true)
                findViewById<AppCompatImageView>(R.id.movieImage_detail).load(
                    "https://image.tmdb.org/t/p/w500"
                            + it.poster_path
                ) {
                    placeholder(R.drawable.ic_baseline_image_24)
                    fallback(R.drawable.ic_baseline_image_24)
                }
                if (it.original_title !=  null) {
                    findViewById<TextView>(R.id.tv_title).text =
                        it.original_title
                } else if (it.original_name != null) {
                    findViewById<TextView>(R.id.tv_title).text =
                        it.original_name
                }
                findViewById<TextView>(R.id.tv_overview).text = it.overview
            }else {
                Toast.makeText(
                    this@MovieDetailActivity,
                    "Ops tivemos um problema, tente novamente em alguns instantes.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun visibilityProgressBar(isLoading: Boolean) {
        progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun visibilityLayoutDescricao(visible: Boolean){
        if (visible){
            descricao.visibility = View.VISIBLE
        }else{
            descricao.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.abs_detail_back_btn) {
            finish()
        }
    }

}