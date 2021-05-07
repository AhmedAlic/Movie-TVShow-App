package  com.myapp

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface API {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "f6f10d60ce6b1904bfca50bd4a7fd036"
    ) : Call<GetMovies>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String = "f6f10d60ce6b1904bfca50bd4a7fd036",
        @Query("query") query: String
    ) : Call<GetMovies>

    @GET("tv/top_rated")
    fun getTopRatedTVShows(
        @Query("api_key") apiKey: String = "f6f10d60ce6b1904bfca50bd4a7fd036"
    ) : Call<GetTVShows>

    @GET("search/tv")
    fun searchTVShows(
        @Query("api_key") apiKey: String = "f6f10d60ce6b1904bfca50bd4a7fd036",
        @Query("query") query: String
    ) : Call<GetTVShows>
}