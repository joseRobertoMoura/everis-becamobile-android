package com.example.movieflix.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.example.movieflix.R
import com.example.movieflix.api.MovieFlixApiTask
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieDetail
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE: String = "EXTRA_MOVIE"
    }

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        getExtras()
        bindViews()
    }

    private fun getExtras() {
        movie = intent.getParcelableExtra(EXTRA_MOVIE)
    }

    private fun bindViews() {
        visibilityProgressBar(true)
        Thread {
            val call = MovieFlixApiTask.retrofitApi()
                .getMovieDetail(movie?.id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
            call.enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    if (response.isSuccessful) {
                        visibilityProgressBar(false)
                        findViewById<AppCompatImageView>(R.id.movieImage_detail).load(
                            "https://image.tmdb.org/t/p/w500"
                                    + response.body()?.poster_path
                        ) {
                            placeholder(R.drawable.ic_baseline_image_24)
                            fallback(R.drawable.ic_baseline_image_24)
                        }
                        if (response.body()?.original_title != null) {
                            findViewById<TextView>(R.id.tv_title).text =
                                response.body()?.original_title
                        } else if (response.body()?.original_name != null) {
                            findViewById<TextView>(R.id.tv_title).text =
                                response.body()?.original_name
                        }
                        findViewById<TextView>(R.id.tv_overview).text = response.body()?.overview
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        "Tivemos um problema, tente novamente em alguns instantes.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun visibilityProgressBar(isLoading: Boolean){
        progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}
