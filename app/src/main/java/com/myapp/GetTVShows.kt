package  com.myapp

import com.google.gson.annotations.SerializedName

data class GetTVShows (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val shows: List<TVShow>,
    @SerializedName("total_pages") val pages: Int
)
