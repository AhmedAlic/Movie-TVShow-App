package  com.myapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TVShowFragment : Fragment() {

    private lateinit var topRatedTVShows: RecyclerView
    private lateinit var topRatedTVShowsAdapter: TVShowsAdapter
    private lateinit var topRatedTVShowsLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topRatedTVShows = view.findViewById(R.id.top_rated_tv_shows)
        topRatedTVShowsLayoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        topRatedTVShows.layoutManager = topRatedTVShowsLayoutManager
        topRatedTVShowsAdapter =
            TVShowsAdapter(mutableListOf()) { show -> showTVShowDetails(view, show) }
        topRatedTVShows.adapter = topRatedTVShowsAdapter
        getTopRatedTVShows()
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
                        searchTVShows(newText)
                    } else {
                        getTopRatedTVShows()
                    }
                    return false
                }
            })
        }
    }

    private fun getTopRatedTVShows() {
        TVShowRepository.getTopRatedTVShows(
            ::onTopRatedTVShowsFetched,
            ::onError
        )
    }

    private fun searchTVShows(searchQuery: String) {
        TVShowRepository.searchTVShows(
            searchQuery,
            ::onSearchedTVShowsFetched,
            ::onError
        )
    }

    private fun onTopRatedTVShowsFetched(shows: List<TVShow>) {
        topRatedTVShowsAdapter.appendTVShows(shows.subList(0, 10))
    }

    private fun onSearchedTVShowsFetched(shows: List<TVShow>) {
        topRatedTVShowsAdapter.appendTVShows(shows)
    }

    private fun showTVShowDetails(view: View, show: TVShow) {
        val intent = Intent(view.context, TVShowDetailsActivity::class.java)
        intent.putExtra(TV_SHOW_TITLE, show.title)
        intent.putExtra(TV_SHOW_OVERVIEW, show.overview)
        intent.putExtra(TV_SHOW_BACKDROP, show.backdropPath)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(view?.context, getString(R.string.error_fetch), Toast.LENGTH_SHORT)
            .show()
    }
}


