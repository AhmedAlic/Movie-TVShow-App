package  com.myapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieFragment : Fragment() {

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topRatedMovies = view.findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutManager
        topRatedMoviesAdapter =
            MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(view, movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter
        getTopRatedMovies()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu,inflater)
        menu.clear()
        inflater.inflate(R.menu.movie_menu, menu)
        val search = menu.findItem(R.id.search_icon)

        if (search != null) {

            val searchView = search.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty() && newText.length > MainActivity.MIN_SEARCH_CHARACTERS) {
                        searchMovies(newText)
                    } else {
                        getTopRatedMovies()
                    }
                    return false
                }
            })
        }
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

    private fun showMovieDetails(view: View, movie: Movie) {
        val intent = Intent(view.context, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(view?.context, getString(R.string.error_fetch), Toast.LENGTH_SHORT)
            .show()
    }
}


