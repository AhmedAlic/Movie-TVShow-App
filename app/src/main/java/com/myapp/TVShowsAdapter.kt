package  com.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TVShowsAdapter (
    private var shows: MutableList<TVShow>,
    private val onTVShowClick: (show: TVShow) -> Unit

) : RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun getItemCount(): Int = shows.size

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    fun appendTVShows(shows: List<TVShow>) {
        this.shows = shows.toMutableList()
        notifyItemRangeChanged(
            this.shows.size,
            shows.size - 1
        )
        notifyDataSetChanged()
    }
    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_tv_show_poster)

        private val title: TextView = itemView.findViewById(R.id.item_tv_show_title)

        fun bind(show: TVShow) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w342${show.posterPath}")
                .circleCrop().into(poster)
            title.text = show.title
            itemView.setOnClickListener { onTVShowClick.invoke(show)}
        }
    }
}