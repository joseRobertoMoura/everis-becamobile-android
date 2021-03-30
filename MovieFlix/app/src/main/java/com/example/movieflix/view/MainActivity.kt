package com.example.movieflix.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieflix.R
import com.example.movieflix.api.MovieFlixApiTask
import com.example.movieflix.model.Movie
import com.example.movieflix.model.MovieTendency
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.moviesList)

        setList()
    }

    private fun setList() {
        loadingVisibility(true)
        Thread {
            val call = MovieFlixApiTask.retrofitApi()
                .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae")
            call.enqueue(object : Callback<MovieTendency> {
                override fun onResponse(
                    call: Call<MovieTendency>,
                    response: Response<MovieTendency>
                ) {
                    if (response.isSuccessful) {
                        loadingVisibility(false)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        val list: MutableList<Movie> = mutableListOf()
                        response.body()?.result?.forEach { list.add(it) }
                        recyclerView.adapter = MoviesAdapter(list)
                    }
                }

                override fun onFailure(call: Call<MovieTendency>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Tivemos um problema, tente novamente em alguns instantes.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }.start()
    }

    private fun loadingVisibility(isLoading: Boolean) {
        progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

