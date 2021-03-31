package com.example.movieflix.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieflix.R
import com.example.movieflix.helper.ClickItemListener
import com.example.movieflix.model.Movie

class MoviesAdapter(private var list: List<Movie>, private var listener: ClickItemListener) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view, list, listener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MoviesViewHolder(
        itemView: View,
        private var list: List<Movie>,
        private var listener: ClickItemListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitl: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        private val image: AppCompatImageView = itemView.findViewById(R.id.movieImage)

        init {
            itemView.setOnClickListener {
                listener.ClickItemMovie(list[adapterPosition])
            }
        }

        fun bind(movie: Movie) {
            if (movie.original_title != null) {
                tvTitl.text = movie.original_title
            } else if (movie.original_name != null) {
                tvTitl.text = movie.original_name
            }
            image.load("https://image.tmdb.org/t/p/w500" + movie.poster_path) {
                placeholder(R.drawable.ic_baseline_image_24)
                fallback(R.drawable.ic_baseline_image_24)
            }

        }
    }


}
