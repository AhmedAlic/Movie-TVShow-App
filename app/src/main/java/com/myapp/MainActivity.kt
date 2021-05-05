package com.myapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val MIN_SEARCH_CHARACTERS = 3
    }

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topRatedMovies = findViewById(R.id.top_rated)
        topRatedMoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutManager
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        MovieRepository.getTopRatedMovies(
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun searchMovies(searchQuery: String) {
        MovieRepository.searchMovies(
            searchQuery,
            ::onSearchedMoviesFetched,
            ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies.subList(0, 10))
    }

    private fun onSearchedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu ?: return false
        menuInflater.inflate(R.menu.movie_menu, menu)
        val search = menu.findItem(R.id.search_icon)

        if (search != null) {

            val searchView = search.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty() && newText.length > MIN_SEARCH_CHARACTERS) {
                        searchMovies(newText)
                    } else {
                        getTopRatedMovies()
                    }
                    return false
                }
            })
        }
        return true
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
