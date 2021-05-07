package com.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter (
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit

) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyItemRangeChanged(
            this.movies.size,
            movies.size - 1
        )
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        private val title: TextView = itemView.findViewById(R.id.item_movie_title)

        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .circleCrop().into(poster)
            title.text = movie.title
            itemView.setOnClickListener { onMovieClick.invoke(movie)}
        }
    }
}