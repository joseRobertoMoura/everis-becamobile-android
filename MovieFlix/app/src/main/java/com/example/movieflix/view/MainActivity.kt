package com.example.movieflix.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.R
import com.example.login.login.view.LoginActivity
import com.example.movieflix.model.helper.ClickItemListener
import com.example.movieflix.model.Movie
import com.example.movieflix.view.MovieDetailActivity.Companion.EXTRA_MOVIE
import com.example.movieflix.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.abs_main_item.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity(), ClickItemListener, View.OnClickListener {

    private var listToSearch: MutableList<Movie> = arrayListOf()
    private var listResultSearch: MutableList<Movie> = arrayListOf()
    private lateinit var mainViewModel: MainViewModel
    private var numPage = 1
    private lateinit var totalPages: String
    private var Loged = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)
        back_btn.setOnClickListener(this)
        next_btn.setOnClickListener(this)
        voltar_btn.setOnClickListener(this)
        search_btn.setOnClickListener(this)
        send_search.setOnClickListener(this)
        btn_exit.setOnClickListener(this)

        if (Loged == -1) {
            login()
        }
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, LOGIN_CODE_SUCCESS)
    }

    override fun onRestart() {
        super.onRestart()
        if (Loged == -1) {
            login()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOGIN_CODE_SUCCESS) {
            Loged = 0
            mainViewObserver(numPage.toString())
        } else if (requestCode == LOGIN_CODE_ERROR) {
            login()
        }
    }

    private fun mainViewObserver(numPage: String) {
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init(numPage)
        loadingVisibility(true)
        visibilityRecyclerView(false)
        mainViewModel.moviesList.observe(this, { list ->
            if (list != null) {
                loadingVisibility(false)
                visibilityRecyclerView(true)
                listToSearch.addAll(list)
                if (listResultSearch.isNotEmpty()) {
                    moviesList.adapter = MoviesAdapter(listResultSearch, this)
                } else {
                    moviesList.adapter = MoviesAdapter(list, this)
                }
            } else {
                loadingVisibility(false)
                Toast.makeText(
                    this,
                    "Ops tivemos um problema, tente novamente mais tarde!",
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

    private fun visibilityRecyclerView(visible: Boolean) {
        if (visible) {
            moviesList.visibility = View.VISIBLE
        } else {
            moviesList.visibility = View.GONE
        }

    }

    override fun ClickItemMovie(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        val id = v.id

        when {
            (id == R.id.back_btn) -> {
                if (numPage > 1) {
                    numPage -= 1
                    mainViewObserver(numPage.toString())
                }
            }

            (id == R.id.next_btn) -> {
                if (numPage <= totalPages.toInt()) {
                    numPage += 1
                    mainViewObserver(numPage.toString())
                }
            }

            (id == R.id.search_btn) -> {
                tv_title_abs_main.visibility = View.GONE
                search_btn.visibility = View.GONE
                text_search.visibility = View.VISIBLE
                send_search.visibility = View.VISIBLE
                back_btn.visibility = View.GONE
                next_btn.visibility = View.GONE
                btn_exit.visibility = View.GONE
            }

            (id == R.id.send_search) -> {
                voltar_btn.visibility = View.VISIBLE
                val title = text_search.text.toString()
                listResultSearch = searchList(listToSearch, title)
                mainViewObserver(numPage.toString())
            }

            (id == R.id.voltar_btn) -> {
                mainViewObserver(numPage.toString())
                text_search.text.clear()
                listResultSearch.clear()

                tv_title_abs_main.visibility = View.VISIBLE
                search_btn.visibility = View.VISIBLE
                text_search.visibility = View.GONE
                send_search.visibility = View.GONE
                voltar_btn.visibility = View.GONE
                back_btn.visibility = View.VISIBLE
                next_btn.visibility = View.VISIBLE
                btn_exit.visibility = View.VISIBLE
            }

            (id == R.id.btn_exit) -> {
                Loged = -1
                login()
                finish()
            }
        }

    }

    private fun searchList(list: MutableList<Movie>, title: String): MutableList<Movie> {
        val listResult: MutableList<Movie> = arrayListOf()
        for (element in list) {
            if (element.original_title?.contains(title) == true) {
                listResult.addAll(listOf(element))
                break
            }
        }

        return listResult
    }

    private companion object {
        const val LOGIN_CODE_SUCCESS = 1
        const val LOGIN_CODE_ERROR = 2
    }

}