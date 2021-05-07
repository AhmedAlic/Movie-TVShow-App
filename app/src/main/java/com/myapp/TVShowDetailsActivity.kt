package  com.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val TV_SHOW_BACKDROP = "extra_tv_show_backdroop"
const val TV_SHOW_TITLE = "extra_tv_show_title"
const val TV_SHOW_OVERVIEW = "extra_tv_show_overview"

class TVShowDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var title: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_details)

        backdrop = findViewById(R.id.tv_show_backdrop)
        title = findViewById(R.id.tv_show_title)
        overview = findViewById(R.id.tv_show_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(TV_SHOW_BACKDROP)?.let { backdropPath ->
            Glide.with(this).load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop()).into(backdrop)
        }

        title.text = extras.getString(TV_SHOW_TITLE, "")
        overview.text = extras.getString(TV_SHOW_OVERVIEW, "")
    }
}