package com.example.movieflix.view

import android.content.Intent
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

        detailViewModel =
            ViewModelProvider.NewInstanceFactory().create(DetailViewModel::class.java)
        detailViewModel.init(movie, this)

        bindViews()

        abs_detail_back_btn.setOnClickListener(this)
    }

    private fun getExtras() {
        movie = intent.getParcelableExtra(EXTRA_MOVIE)
    }

    private fun bindViews() {
        visibilityProgressBar(true)
        detailViewModel.moviesList.observe(this,{ it ->
            if (it){
                val poster_path = sharedPreferences.getString("poster_path")
                val original_title = sharedPreferences.getString("original_title")
                val original_name = sharedPreferences.getString("original_name")
                val overview = sharedPreferences.getString("overview")
                visibilityProgressBar(false)
                findViewById<AppCompatImageView>(R.id.movieImage_detail).load(
                    "https://image.tmdb.org/t/p/w500"
                            + poster_path
                ) {
                    placeholder(R.drawable.ic_baseline_image_24)
                    fallback(R.drawable.ic_baseline_image_24)
                }
                if (original_title != null) {
                    findViewById<TextView>(R.id.tv_title).text =
                        original_title
                } else if (original_name != null) {
                    findViewById<TextView>(R.id.tv_title).text =
                        original_name
                }
                findViewById<TextView>(R.id.tv_overview).text = overview
            }else {
                Toast.makeText(
                    this@MovieDetailActivity,
                    "Tivemos um problema, tente novamente em alguns instantes.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
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

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.abs_detail_back_btn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}