package com.myapp

import com.google.gson.annotations.SerializedName

data class GetMovies (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
